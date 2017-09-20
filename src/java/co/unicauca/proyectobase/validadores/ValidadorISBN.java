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
@FacesValidator(value="validadorISBN")
public class ValidadorISBN implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String isbn = value.toString();
        
        if(!isValidoFormato(isbn)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "El ISBN deben ser numeros en este formato \"xxx-xx-xxxxx-xx-x\".");
            throw new ValidatorException(msg);
        }

    }
    
    /*La validacion del ISBN sera con respecto a los 13 digitos que establecieron
    * a partir de enero de 2007. Formato: xxx-xx-xxxxx-xx-x donde las x son numeros*/
    public boolean isValidoFormato(String isbn){
        boolean valido = true;
        String[] dividido = isbn.split("-");
        int tamano = dividido.length;
        int i;
        if(dividido.length == 5){
            for (i = 0; i < tamano; i++) {
                /*Se comprueban que los grupos sean numeros*/
                if(isNumero(dividido[i]) == false){
                    valido = false;
                    i = tamano;
                } 
            }
            if(valido){
                for (i = 0; i < tamano; i++) {
                    
                    //Comprobando el tamaño de los grupos de numeros
                    if(i == 0){
                        if(dividido[i].length() != 3){valido = false;}
                    }
                    if(i == 1){
                        if(dividido[i].length() != 2){valido = false;}
                    }
                    if(i == 2){
                        if(dividido[i].length() != 5){valido = false;}
                    }
                    if(i == 3){
                        if(dividido[i].length() != 2){valido = false;}
                    }
                    if(i == 4){
                        if(dividido[i].length() != 1){valido = false;}
                    }
                    if(valido == false){
                        System.out.println("tamano de grupos de numeros no es correcto");
                        i = tamano;
                    }
                }
            }else{
                System.out.println("solo se permiten numeros y el caracter '-'");
            }
        }else{
            System.out.println("El ISBN deben ser 5 grupos de numeros");
            valido = false;
        }
        return valido;
    }
    
    /*Validar que el string sean numeros*/
    public boolean isNumero(String cadena) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(cadena); 
        return m.find();
    }
    
}