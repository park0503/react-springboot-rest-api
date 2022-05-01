package com.example.gccoffee.repository;

import com.example.gccoffee.domain.Category;
import com.example.gccoffee.domain.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class ProductJdbcRepositoryTest {
    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "1q2w3e4r")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @Autowired
    ProductRepository repository;

    private static Product newProduct = new Product(UUID.randomUUID(), "new-product", Category.COFFEE_BEAN_PACKAGE, 1000L);

    @Test
    @Order(1)
    @DisplayName("상품을 추가할 수 있다.")
    void testInsert() {
        repository.insert(newProduct);
        List<Product> all = repository.findAll();
        assertThat(all.isEmpty(), is(false));
    }

    @Test
    @Order(2)
    @DisplayName("상품을 이름으로 조회할 수 있다.")
    void testFindByName() {
        Optional<Product> product = repository.findByName(newProduct.getProductName());
        assertThat(product.isEmpty() ,is(false));
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(3)
    @DisplayName("상품을 아이디로 조회할 수 있다.")
    void testFindById() {
        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty() ,is(false));
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(4)
    @DisplayName("상품들을 카테고리로 조회할 수 있다.")
    void testFindByCategory() {
        List<Product> products = repository.findByCategory(newProduct.getCategory());
        assertThat(products.isEmpty() ,is(false));
    }

    @Test
    @Order(5)
    @DisplayName("상품들을 전체 삭제할 수 있다.")
    void testDeleteAll() {
        repository.deleteAll();
        List<Product> products = repository.findByCategory(newProduct.getCategory());
        assertThat(products.isEmpty() ,is(true));
    }

    @Test
    @Order(6)
    @DisplayName("상품을 수정할 수 있다.")
    void testUpdate() {
        repository.insert(newProduct);
        newProduct.setProductName("update-product");
        newProduct.setPrice(20L);
        newProduct.setDescription("ㅋㅋㄹㅃㅃ");
        repository.update(newProduct);

        Optional<Product> product = repository.findById(newProduct.getProductId());
        assertThat(product.isEmpty(), is(false));
        assertThat(product.get(), samePropertyValuesAs(newProduct));
    }

    @Test
    @Order(7)
    @DisplayName("상품을 아이디로 삭제할 수 있다.")
    void testDeleteById() {
        repository.deleteById(newProduct.getProductId());
        List<Product> products = repository.findAll();
        assertThat(products.isEmpty(), is(true));
    }
}