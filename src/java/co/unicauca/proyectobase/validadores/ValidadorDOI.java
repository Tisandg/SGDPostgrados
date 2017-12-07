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
 * Clase que permite hacer las validaciones para el DOI de alguna publicación.
 * Esta clase es usada para el registro de libro, revista, evento, publicación.
 * @author Juan
 */
@FacesValidator(value="validadorDOI")
public class ValidadorDOI implements Validator 
{
    /**
     * Método que es implementado de la clase Validator para realizar las validaciones del objeto value
     * que en este caso se obtiene el DOI desde la vista de registro de publicación
     * y es transformado en una cadena (String)
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String doi = value.toString();
        if(doi.length() != 0) 
        {
            if(!validarFormato(doi)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El DOI debe tener el siguiente formato numérico: 10.xxxx/xxxx.");
                throw new ValidatorException(msg);
            }
            else {
                if(!validarSufijo(doi)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede iniciar con un caracter especial despues del símbolo '/'.");
                    throw new ValidatorException(msg);
                }
                else {
                    if(!validarCaracteresPrefijo(doi)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El prefijo solo puede ser numérico.");
                        throw new ValidatorException(msg);
                    }

                    if(!validarPrefijo(doi)) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El inicio del DOI no cumple con el formato numerico: 10.1234.");
                        throw new ValidatorException(msg);
                    }
                }
            }
        }
    }
    
    /**
     * validar que el DOI tenga el formato "prefijo"/"sufijo"
     * @param doi
     * @return 
     */
    public boolean validarFormato(String doi) {
        return doi.split("/").length == 2;
    }
    
    /**
     * valida que el sufijo no inicie con algun caracter especial, solo debe iniciar con una letra o un numero
     * @param doi
     * @return 
     */
    public boolean validarSufijo(String doi) {        
        String[] valores = doi.split("/");
        
        Pattern p = Pattern.compile("^([0-9a-zA-Z])");
        Matcher m = p.matcher(valores[1]);
        return m.find();
    }
    
    /**
     * valida que el prefijo cumpla con el formato 10.xxxx
     * @param doi
     * @return 
     */
    public boolean validarPrefijo(String doi) {        
        String[] valores = doi.split("/");
        
        Pattern p = Pattern.compile("(^(10.)+([0-9]{4}))$");
        Matcher m = p.matcher(valores[0]);
        return m.find();
    }
    
    /**
     * valida que el prefijo solo tenga numeros y un punto
     * @param doi
     * @return 
     */
    public boolean validarCaracteresPrefijo(String doi) {        
        String[] valores = doi.split("/");

        Pattern p = Pattern.compile("^[0-9.]*$");
        Matcher m = p.matcher(valores[0]);
        return m.find();
    }
    
}


