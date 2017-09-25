/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Juan
 */
@FacesValidator(value="validadorAutoresSecundarios")
public class ValidadorAutoresSecundarios implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);
        
        if(!texto.equals("")) {
            if(validarInicioEspacio(texto) || validarInicioCaracteres(texto)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede inicar con un espacio en blanco, número o caracter especial");
                throw new ValidatorException(msg);
            }
            
            if(validarTerminacion(texto.substring(texto.length() - 1))) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No debe terminar con un caracter especial o un número");
                    throw new ValidatorException(msg);
                }
                
                if(!validarNombreAutores(texto)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Uno o varios nombres no cumplen con el formato: Nombre(s) Apellido(s)");
                    throw new ValidatorException(msg);
                }
         
                //Pattern p = Pattern.compile("^([^0-9\\-\\_\\,\\;\\.]+[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s;]*+[^;])+$");
//                Pattern p = Pattern.compile("^([^_*¿?!¡#-.$%&+=’~{}^<>;0-9]+[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s;]*+[^;])+$");
//                //Pattern p = Pattern.compile("^([^_*¿?!¡#-.$%&+=’~{}^<>;0-9\\s]+[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s;]*+[^_*¿?!¡#-.$%&+=’~{}^<> ;0-9])+$");
//                Matcher m = p.matcher(texto);

//                if(validarFormato(texto.substring(texto.length() - 1))) {
//                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No debe terminar con un caracter especial o un número");
//                    throw new ValidatorException(msg);
//                }                

                
                
            
            
        }
        

    }
    
    //valida que el correo no empieze por un espacio en blanco
    public boolean validarInicioEspacio(String texto) {
        Pattern p = Pattern.compile("^[\\s_*'¿?!¡#-.$%&+=’~{}^<>;0-9]");
        Matcher m = p.matcher(texto);
        return m.find();
    }
    
    //valida que el correo no empieze por un caracter especial o numeros
    public boolean validarInicioCaracteres(String texto) {
        Pattern p = Pattern.compile("^[_*¿?!¡#-.$%&+=’~{},:\\[]^<>;0-9]");
        Matcher m = p.matcher(texto);
        return m.find();
    }
    
    //valida que el correo no empieze por un caracter especial o numeros
    public boolean validarFormato(String texto) {
        //Pattern p = Pattern.compile("^([^\\s]+[A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s;]*+[^;])+$");
        Pattern p = Pattern.compile("^([A-ZÁÉÍÓÚ]{1}[a-zñáéíóú]+[\\s;]*+[^;])+$");
        Matcher m = p.matcher(texto);
        return m.find();
    }
    
    //valida que la cadena ingresada no termine en un ;
    public boolean validarTerminacion(String texto) {
        Pattern p = Pattern.compile("[_*¿?!¡#-.$%;&+=’~{}<>0-9]");
        Matcher m = p.matcher(texto);
        return m.find();
    }
    
    
    //valida que la cadena ingresada no termine en un ;
    public boolean validarNombreAutores(String texto) {
        
        String[] nombres;
        nombres = texto.split(";");
        
        for (String nombre : nombres) {
            if (!(nombre).matches("([A-Za-z[ÁÉÍÓÚÑñáéíóú]]+[\\s;]*)+$")) {
                return false; 
            }
        }
        return true;
    }
    
    
}
