/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.utils.EntityManagerHelper;
import ferreteria_las_vegas.utils.LoggerManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author johan
 */
public class EstadisticasJPAController {

    private static EstadisticasJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (EstadisticasJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new EstadisticasJPAController();
                }
            }
        }
    }

    public static EstadisticasJPAController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public List<Object[]> ConsultarArticuloMasVendido(LocalDate dIncial, LocalDate dFinal) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ar.Art_Codigo,\n"
                    + "    CONCAT(ar.Art_Nombre, '/', ar.Art_Marca),\n"
                    + "    SUM(af.Art_Cantidad) AS c\n"
                    + "FROM\n"
                    + "    tb_articulosxfactura AS af\n"
                    + "        INNER JOIN\n"
                    + "    tb_articulos ar ON af.Art_Articulo = ar.Art_Codigo\n"
                    + "        INNER JOIN\n"
                    + "    tb_facturas fa ON af.Art_Factura = fa.Fac_Codigo\n"
                    + "WHERE\n"
                    + "    fa.Fac_Fecha between ?1 AND ?2\n"
                    + "GROUP BY af.Art_Articulo\n"
                    + "ORDER BY c DESC\n"
                    + "LIMIT 10;");

            DateTimeFormatter dt2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            q.setParameter(1, dIncial.format(dt2));
            q.setParameter(2, dFinal.format(dt2));
            List<Object[]> result = q.getResultList();
            return result;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            System.out.println(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            System.out.println(ex.toString());
            return null;
        }
    }

    public List<Object[]> ConsultarArticuloMenosVendido(LocalDate dIncial, LocalDate dFinal) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ar.Art_Codigo,\n"
                    + "    CONCAT(ar.Art_Nombre, '/', ar.Art_Marca),\n"
                    + "    SUM(af.Art_Cantidad) AS c\n"
                    + "FROM\n"
                    + "    tb_articulosxfactura AS af\n"
                    + "        INNER JOIN\n"
                    + "    tb_articulos ar ON af.Art_Articulo = ar.Art_Codigo\n"
                    + "        INNER JOIN\n"
                    + "    tb_facturas fa ON af.Art_Factura = fa.Fac_Codigo\n"
                    + "WHERE\n"
                    + "    fa.Fac_Fecha between ?1 AND ?2\n"
                    + "GROUP BY af.Art_Articulo\n"
                    + "ORDER BY c ASC\n"
                    + "LIMIT 10;");

            DateTimeFormatter dt2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            q.setParameter(1, dIncial.format(dt2));
            q.setParameter(2, dFinal.format(dt2));
            List<Object[]> result = q.getResultList();
            return result;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            System.out.println(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            System.out.println(ex.toString());
            return null;
        }
    }

    public List<Object[]> ConsultarMejorCliente(LocalDate dIncial, LocalDate dFinal) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    pe.Per_Cedula,CONCAT(pe.Per_Nombre,\" \",pe.Per_PApellido,\" \",pe.Per_SApellido), COUNT(fa.Fac_Cliente) c, SUM(fa.Fac_Total) s\n"
                    + "FROM\n"
                    + "	tb_clientes AS cl\n"
                    + "    INNER JOIN tb_personas AS pe\n"
                    + "    ON cl.Cli_Persona = pe.Per_Cedula\n"
                    + "    INNER JOIN tb_facturas AS fa\n"
                    + "    ON fa.Fac_Cliente = cl.Cli_Persona    \n"
                    + "    WHERE fa.Fact_EstadoPago = 'I' AND fa.Fac_Fecha between ?1 AND ?2\n"
                    + "    GROUP BY pe.Per_Cedula \n"
                    + "    ORDER BY c, s DESC\n"
                    + "    LIMIT 10;");

            DateTimeFormatter dt2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            q.setParameter(1, dIncial.format(dt2));
            q.setParameter(2, dFinal.format(dt2));
            List<Object[]> result = q.getResultList();
            return result;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            System.out.println(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            System.out.println(ex.toString());
            return null;
        }
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
