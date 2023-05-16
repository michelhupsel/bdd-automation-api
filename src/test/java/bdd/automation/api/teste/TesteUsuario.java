/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package bdd.automation.api.teste;

import bdd.automation.api.dominio.Usuario;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TesteUsuario extends TesteBase {

    private static final String LISTA_USUARIOS_ENDPOINT = "/users";
    private static final String CRIA_USUARIOS_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIO_ENDPOINT = "/users/{userID}";

    @Test
    public void testeMostraPaginaEspecifica() {
        given().
                params("page", "2").
                when().
                get(LISTA_USUARIOS_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body("page", is(2)).
                body("data", is(notNullValue()));
    }

    @Test
    public void testeCriaUsuarioComSucesso() {
        Usuario usuario = new Usuario("Michel", "Analista", "email@gmail.com");
        given().
                body(usuario).
                when().
                post(CRIA_USUARIOS_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("Michel"));
    }

    @Test
    public void testeTamanhoDosItensMostradosIgualAoPerPage() {
        int paginaEsperada = 2;
        int perPageEsperado = retornaPerPageEsperado(paginaEsperada);
        given().
                params("page", paginaEsperada).
                when().
                get(LISTA_USUARIOS_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "page", is(paginaEsperada),
                        "data.size()", is(perPageEsperado),
                        "data.findAll {it.avatar.startsWith('https://reqres.in/')}.size()", is(perPageEsperado)
                );
    }

    @Test
    public void testeMostraUsuarioEspecifico() {
        Usuario usuario = given().
                pathParam("userID", 2).
                when().
                get(MOSTRAR_USUARIO_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                extract().
                body().jsonPath().getObject("data",Usuario.class);

        assertThat(usuario.getEmail(),containsString("@reqres.in"));
        assertThat(usuario.getName(),is("Janet"));
        assertThat(usuario.getLastName(),is("Weaver"));

    }

    private static int retornaPerPageEsperado(int page) {
        int perPageEsperado = given()
                .param("page", page)
                .when()
                .get(LISTA_USUARIOS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("per_page");
        return perPageEsperado;
    }
}
