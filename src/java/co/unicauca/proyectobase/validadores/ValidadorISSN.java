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
@FacesValidator(value = "validadorISSN")
public class ValidadorISSN implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String issn = value.toString();

        if(issn.length() == 0) 
        {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Se debe registrar el ISSN de la revista");
            throw new ValidatorException(msg);
        }
        
        if (issn.length() != 0) {

            if (issn.length() != 8) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Longitud del ISSN es incorrecta, debe ser de 8 numeros o 7 numeros y una X ");
                throw new ValidatorException(msg);
            }

            if (!validarFormato(issn)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Formato del ISSN es incorrecto, debe ser de 8 numeros o 7 numeros y una X ");
                throw new ValidatorException(msg);
            }
        }

    }

    //valida el formato del DOI
    public boolean validarFormato(String doi) {
        //  Pattern p = Pattern.compile("(^([0-9]{4})+([0-9]{3})+([0-9X]{1}))$");
        Pattern p = Pattern.compile("(^([0-9]{4})+([0-9]{3})+([0-9X]{1}))$");
        //http://www.issn.org/es/comprender-el-issn/que-es-el-numero-issn/
        //https://goo.gl/jQFjlJ
        //Pattern p = Pattern.compile("^([0-9])");
        Matcher m = p.matcher(doi);
        return m.find();
    }

}
