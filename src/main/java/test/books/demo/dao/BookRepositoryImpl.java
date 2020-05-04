package test.books.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import test.books.demo.entity.Book;
import test.books.demo.exception.ObjectNotFoundException;
import test.books.demo.settings.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final Settings settings;

    @Autowired
    public BookRepositoryImpl(NamedParameterJdbcTemplate namedJdbcTemplate, Settings settings) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.settings = settings;
    }

    @Override
    public List<Book> getAll() {
        String sql = "SELECT id, name, price, image FROM book book";

        return namedJdbcTemplate
                .query(sql, (rs, rowNum) ->
                                Book.builder()
                                        .id(rs.getString("id"))
                                        .name(rs.getString("name"))
                                        .price(rs.getBigDecimal("price"))
                                        .image(rs.getString("image"))
                                        .build()
                );
    }

    private List<Book> getByIdList(List<String> ids) {
        String sql = "SELECT id, name, price, image FROM book book WHERE id IN (:ids)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ids", ids);

        return namedJdbcTemplate
                .query(sql, paramMap, (rs, rowNum) ->
                        Book.builder()
                                .id(rs.getString("id"))
                                .name(rs.getString("name"))
                                .price(rs.getBigDecimal("price"))
                                .image(rs.getString("image"))
                                .build()
                );
    }

    @Override
    public Book get(String id) {
        String sql = "SELECT id, name, details, price, image FROM book book WHERE id = :id";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("simLevel", settings.getSimLevel()); // move to Settings

        Book.BookBuilder bookBuilder = DataAccessUtils.singleResult(namedJdbcTemplate
                .query(sql, paramMap, (rs, rowNum) ->
                        Book.builder()
                                .id(rs.getString("id"))
                                .name(rs.getString("name"))
                                .price(rs.getBigDecimal("price"))
                                .image(rs.getString("image"))
                                .details(rs.getString("details"))
                )
        );

        if (bookBuilder == null) {
            throw new ObjectNotFoundException(String.format("Book %s is not found", id));
        }

        // How to calc similarity. Assume there are the following rows in books_views
        // User1 B1 B2
        // User2    B2
        // User3 B1 B2
        // sim(i, j) = vector_i * vector_j / (sqrt(vector_i^2) * sqrt(vector_j^2))
        // Vector for B1 = 1 0 1
        // Vector for B2 = 1 1 1
        // sim(B1, B2) = (1*1 + 0*1 + 1*1) / ((sqrt(1^2 + 0^2 + 1^2) *  sqrt(1^2 + 1^2 + 1^2))

        String sqlSimilarity = "" +
                "WITH CalcSQRT AS (" +
                "   SELECT book_id, SQRT(COUNT(user)) AS value FROM books_views GROUP BY book_id), " +
                "CountUsers AS (" +
                "   SELECT " +
                "       views.book_id, " +
                "       COUNT(views.user) AS value " +
                "   FROM " +
                "       books_views views " +
                "   JOIN " +
                "       books_views base " +
                "   ON " +
                "           not views.book_id = :id " +
                "       AND base.book_id = :id " +
                "       AND base.user = views.user" +
                "   GROUP BY " +
                "       views.book_id" +
                "   ) " +
                "" +
                "SELECT " +
                "   CountUsers.book_id " +
                "FROM " +
                "   CountUsers " +
                "JOIN " +
                "   CalcSQRT " +
                "ON " +
                "   CalcSQRT.book_id = CountUsers.book_id " +
                "JOIN " +
                "   CalcSQRT sqrtBase " +
                "ON " +
                "   sqrtBase.book_id = :id " +
                "WHERE " +
                "   CountUsers.value / (CalcSQRT.value * sqrtBase.value) > :simLevel";

        List<String> similarBooksId = namedJdbcTemplate.query(sqlSimilarity, paramMap, SingleColumnRowMapper.newInstance(String.class));

        return bookBuilder.similarBooks(getByIdList(similarBooksId)).build();
    }
}
