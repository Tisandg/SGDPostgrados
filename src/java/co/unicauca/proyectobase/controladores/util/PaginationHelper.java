package co.unicauca.proyectobase.controladores.util;

import javax.faces.model.DataModel;

/***
 * Clase con métodos que permiten realizar la paginación.
 * @author 
 */

public abstract class PaginationHelper {

    private int pageSize;
    private int page;

    /**
     * Método que establece el tamaño de la página.
     * @param pageSize: tamaño de la página
     */
    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * Declaración de métodos de páginación. 
     */
    public abstract int getItemsCount();

    public abstract DataModel createPageDataModel();

    /**
     * Método que permite obtener el primer item de la página.
     * @return primer item de la lista
     */
    public int getPageFirstItem() {
        return page * pageSize;
    }

    /**
     * Método que permite obtener el número del último item de la página.
     * @return número del último item de la página.
     */
    public int getPageLastItem() {
        int i = getPageFirstItem() + pageSize - 1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    /**
     * Método que permite verificar si hay una próxima página a la actual.
     * @return valores de la siguiente página
     */
    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }

    /**
     * Método que permite obtener la próxima página
     */
    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    /**
     * Método que permite verificar si hay una página anterior a la actual
     * @return 
     */
    public boolean isHasPreviousPage() {
        return page > 0;
    }

    /**
     * Método que permite volver a la página anterior
     */
    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }

    /**
     * Método que permite obtener el tamaño de la página.
     * @return 
     */
    public int getPageSize() {
        return pageSize;
    }

}
