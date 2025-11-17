package com.example.lvl_up

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.lvl_up.data.User
import com.example.lvl_up.data.UserDAO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDAO

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertarYLeerUsuario() = runBlocking {
        val usuario = User(
            id = 1, nombre = "Prueba Usuario", rut = "11.111.111-1",
            correo = "prueba@test.com", contrasena = "12345678",
            rol = "Cliente", avatarUrl = "url"
        )
        userDao.insertUser(usuario)
        val usuarioLeido = userDao.getUserById(1)
        assertNotNull(usuarioLeido)
        assertEquals("Prueba Usuario", usuarioLeido?.nombre)
    }

    @Test
    @Throws(Exception::class)
    fun actualizarUsuario() = runBlocking {
        val usuarioInicial = User(
            id = 1, nombre = "Nombre Original", rut = "11.111.111-1",
            correo = "prueba@test.com", contrasena = "12345678",
            rol = "Cliente", avatarUrl = "url"
        )
        userDao.insertUser(usuarioInicial)

        val usuarioActualizado = User(
            id = 1, nombre = "Nombre Actualizado", rut = "11.111.111-1",
            correo = "prueba@test.com", contrasena = "passNuevo",
            rol = "Administrador", avatarUrl = "url"
        )
        userDao.updateUser(usuarioActualizado)

        val usuarioLeido = userDao.getUserById(1)
        assertEquals("Nombre Actualizado", usuarioLeido?.nombre)
        assertEquals("Administrador", usuarioLeido?.rol)
    }
}