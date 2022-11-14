package com.example.tpBD.repository

import com.example.tpBD.model.ContenidoG
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface ContenidoGRepository : JpaRepository<ContenidoG , Long> {

    @Query(
        value = "select * from contenido  where ID_CONTENIDO = :id",
        nativeQuery = true
    )
    fun  buscarContenidoPorId(@Param("id") id :Long) : ContenidoG

    //VER TODOS LOS CONTENIDOS
    @Query(
        value = "SELECT * FROM contenido",
        nativeQuery = true
    )
    fun todosLosContenidos() : List<ContenidoG>

    //ELIMINAR UN CONTENIDO
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM CONTENIDO WHERE ID_CONTENIDO=:id",
        nativeQuery = true
    )
    fun eliminarContenido(@Param("id") id :Long)

    //GUARDAR UN CONTENIDO
    @Modifying
    @Transactional
    @Query(
        value = "INSERT  CONTENIDO (TITULO,EXTENSION,URL) VALUES(:titulo,:extension, :url)" ,
        nativeQuery = true
    )
    fun agregarContenido(@Param("titulo") titulo: String ,
                         @Param("extension") extension: String,
                         url: String
    )

    //ACTUALIZAR UN CONTENIDO
    @Modifying
    @Transactional
    @Query(
        value = "UPDATE contenido SET TITULO = :titulo, EXTENSION = :extension WHERE ID_CONTENIDO = :id" ,
        nativeQuery = true
    )
    fun actualizarContenido(@Param("titulo") titulo: String ,
                            @Param("extension") extension: String,
                            @Param("id") id : Long
    )


    //FILTRAR POR CATEGORIA
    @Query(
        value = "select * from contenido  where ID_TIPO_CONTENIDO = :categoria",
        nativeQuery = true
    )
    fun  buscarPorCategoria(@Param("categoria") categoria :Number) : List<ContenidoG>

    @Query(value = "SELECT DISTINCT cont.*\n" +
            "from SE_CLASIFICA_EN claf\n" +
            "         INNER JOIN contenido cont " +
            "ON cont.ID_CONTENIDO = claf.ID_CONTENIDO\n" +
            "         INNER JOIN categoria cat " +
            "ON cat.ID_CATEGORIA = claf.ID_CATEGORIA\n" +
            "WHERE cat.TIPO IN :categorias\n" +
            "order by cont.ID_CONTENIDO DESC", nativeQuery = true)
    fun buscarPorCategorias(categorias :List<String>) :List<ContenidoG>
}