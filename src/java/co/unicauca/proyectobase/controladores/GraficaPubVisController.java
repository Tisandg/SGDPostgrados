package co.unicauca.proyectobase.controladores;

import co.unicauca.proyectobase.dao.PublicacionFacade;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
 
@Named(value = "graficaPubVisController")
@ManagedBean
@SessionScoped
public class GraficaPubVisController implements Serializable {

    @EJB
    private PublicacionFacade dao;
    private BarChartModel barModel;
    private String totalPub;
    private String anio;
    private String anioAux;
    @PostConstruct
    public void init() {
        this.anio="2017";
        createBarModels();
    }
    
    public void iniciar() {
        createBarModels();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private void createBarModels() {
        createBarModel();

    }

    public String getTotalPub() {
        return totalPub;
    }

    public void setTotalPub(String totalPub) {
        this.totalPub = totalPub;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getAnioAux() {
        return anioAux;
    }

    public void setAnioAux(String anioAux) {
        this.anioAux = anioAux;
    }
    

    private ArrayList<Integer> ContMeses() {
        String auxAnio = anio;
        String mes;
        ArrayList<Integer> contMeses = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            mes = "" + i;
            //contMeses.add(dao.CountByMonthYear(anio, mes));
            contMeses.add(dao.CountByMonthYearVis(auxAnio, mes));
        }
        return contMeses;
    }

    private int toltalContMeses(ArrayList<Integer> contMeses) {

        int sum = 0;
        for (int i = 0; i < contMeses.size(); i++) {
            sum = sum + contMeses.get(i);
        }
        return sum;
    }

    private void createBarModel() {

        ArrayList<Integer> resMes = ContMeses();
        barModel = new BarChartModel();

        ChartSeries meses = new ChartSeries();
        meses.setLabel("Mes");
        meses.set("Enero", resMes.get(0));
        meses.set("Febrero", resMes.get(1));
        meses.set("Marzo", resMes.get(2));
        meses.set("Abril", resMes.get(3));
        meses.set("Mayo", resMes.get(4));
        meses.set("Junio", resMes.get(5));
        meses.set("Julio", resMes.get(6));
        meses.set("Agosto", resMes.get(7));
        meses.set("Septiembre", resMes.get(8));
        meses.set("Octubre", resMes.get(9));
        meses.set("Noviembre", resMes.get(10));
        meses.set("Diciembre", resMes.get(11));

        barModel.addSeries(meses);

        barModel.setTitle("Grafica Publicaciones Visadas "+anio);
        barModel.setLegendPosition("ne");
       
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Mes");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Numero Publicaciones");

        yAxis.setMin(0);
        int numMaximo = Collections.max(resMes) + 10;
        //yAxis.setMax(200);
        yAxis.setMax(numMaximo);
        int numTotalPub = toltalContMeses(resMes);
        setTotalPub("" + numTotalPub);
    }

}
