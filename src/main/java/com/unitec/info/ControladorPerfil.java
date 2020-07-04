package com.unitec.info;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Representational State Transfer Controller
@RestController
@RequestMapping("/api") //Aplication Programing Interface
public class ControladorPerfil {

    //Esta es la inversion de control o inyeccion de dependencia
    @Autowired
    RepoPerfil repoPerfil;

    //En los servicios Rest se tienen una urlBase que consiste en la ip o host
    //Es decir para este caso mi api REST es:
    //http://localhost:8080/api/hola
    @GetMapping("/hola")
    public Saludo saludar() {
        Saludo s = new Saludo();
        s.setNombre("Carlos");
        s.setMensaje("Mi primer mensaje en spring rest");
        return s;
    }
    //El siguiente metodo va a servir para guardar en un backend nuestros datos del perfil
    //para guardar siempre debes uasr el metodo post

    @PostMapping("/perfil")
    public Estatus guardar(@RequestBody String json) throws Exception {
        //[ara recibir obeto json es leerlo y convertirlo
        //en objeto JAVA a esto se le llama des-serializacion
        ObjectMapper maper = new ObjectMapper();
        Perfil perfil = maper.readValue(json, Perfil.class);
        //por experiencia antes de guardar debemos checar que llego bien todo el objeto json y se leyo bien
        System.out.println("Perfil leido " + perfil);

        //Aqui este objeto perfil despues se guarda con una sola linea en mongoDB
        //Aqui va a ir la linea para guardar
        repoPerfil.save(perfil);

        //Despues enviamos un mensaje de eststuas al cliente para informar si se guardo o no su perfil
        Estatus e = new Estatus();
        e.setSuccess(true);
        e.setMensaje("Perfil guardado con exito");
        return e;
    }

//Vamos a generar nuestro servicio para actualizar un perfil
    @PutMapping("/perfil")
    public Estatus actualizar(@RequestBody String json) throws Exception {
        ObjectMapper maper = new ObjectMapper();
        Perfil perfil = maper.readValue(json, Perfil.class);
        //por experiencia antes de guardar debemos checar que llego bien todo el objeto json y se leyo bien
        System.out.println("Perfil leido " + perfil);

        //Aqui este objeto perfil despues se guarda con una sola linea en mongoDB
        //Aqui va a ir la linea para guardar
        repoPerfil.save(perfil);

        //Despues enviamos un mensaje de eststuas al cliente para informar si se guardo o no su perfil
        Estatus e = new Estatus();
        e.setSuccess(true);
        e.setMensaje("Perfil actualizado con exito");
        return e;
    }

    //El metodo para borrar un perfil
    @DeleteMapping("/perfil/{id}")
    public Estatus borrar(@PathVariable String id){
        //Invocamos el repositorio
        repoPerfil.deleteById(id);
        //Generaamos el mensaje de estatus para que este informado el cliente
        Estatus e=new Estatus();
        e.setMensaje("Perfil borrado con exito");
        e.setSuccess(true);
        return e;
    }
    
    //El metodo para buscar todos
    @GetMapping("/perfil")
    public List<Perfil> buscarTodos(){
        return repoPerfil.findAll();
    }
    
    //El metodo para buscar por id
    @GetMapping("/perfil/{id}")
    public Perfil buscarPorId(@PathVariable String id){
        return repoPerfil.findById(id).get();
    }
    
    
}
