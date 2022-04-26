package com.example.gccoffee.repository;

import com.example.gccoffee.domain.Category;
import com.example.gccoffee.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.gccoffee.util.JdbcUtils.toLocalDateTime;
import static com.example.gccoffee.util.JdbcUtils.toUUID;

@Repository
public class ProductJdbcRepository implements ProductRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from products", productRowMapper);
    }

    @Override
    public Product insert(Product product) {
        var update =  jdbcTemplate.update("insert into products values(UNHEX(REPLACE(:productId, '-', '')), :productName, :category, :price, :description, :createdAt, :updatedAt)", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        var update =  jdbcTemplate.update("update products set product_name = :productName, price = :price, category = :category, description = :description, updated_at = :updatedAt where product_id = UNHEX(REPLACE(:productId, '-', ''))", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return product;
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(
                            "select * from products where product_id = UNHEX(REPLACE(:productId, '-', ''))"
                            , Collections.singletonMap("productId", productId.toString().getBytes())
                            , productRowMapper
                    )
            );
        } catch(EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByName(String productName) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(
                            "select * from products where product_name = :productName"
                            , Collections.singletonMap("productName", productName)
                            , productRowMapper
                    )
            );
        } catch(EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return jdbcTemplate.query(
                "select * from products where category = :category"
                , Collections.singletonMap("category", category.toString()),
                productRowMapper
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from products", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID productId) {
        jdbcTemplate.update("delete from products where product_id = UNHEX(REPLACE(:productId, '-', ''))", Collections.singletonMap("productId", productId.toString().getBytes()));
    }

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        UUID productId = toUUID(resultSet.getBytes("product_id"));
        String productName = resultSet.getString("product_name");
        Category category = Category.valueOf(resultSet.getString("category"));
        Long price = resultSet.getLong("price");
        String description = resultSet.getString("description");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Product(productId, productName, category, price, description, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(Product product) {
        Map paramMap = new HashMap<String, Object>();
        paramMap.put("productId", product.getProductId().toString().getBytes());
        paramMap.put("productName", product.getProductName());
        paramMap.put("category", product.getCategory().toString());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());
        return paramMap;
    };
}
