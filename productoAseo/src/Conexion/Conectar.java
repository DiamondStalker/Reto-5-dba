package Conexion;

import java.sql.*;
import javax.swing.*;

/**
 *
 * @author DiamondStalker
 */

public class Conectar {

    Connection conect = null;

    public Connection conexion() {
        try {
            //Cargamos el Driver MySQL
            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.gjt.mm.mysql.Driver");
            conect = DriverManager.getConnection("jdbc:mysql://localhost/inventario?"
                    + "user=root&password=");

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexion" + e);
        }
        return conect;
    }
    
    public boolean nuevoproducto(String Nombre,int Cantidad, int Precio, String Olor,String Date) {
        try {

            Connection cn = conexion();
            PreparedStatement rs = cn.prepareStatement("INSERT INTO productoaseo"
                    + "(nombre,cantidad,precio,olor,fecha_vencimiento)"
                    + "VALUES (?,?,?,?,?)");

            rs.setString(1, Nombre);
            rs.setInt(2, Cantidad);
            rs.setInt(3, Precio);
            rs.setString(4, Olor);
            rs.setString(5, Date);

            rs.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se inserto correctamente ");
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error al insertar" + e);
            return false;
        }
        return true;
    }
    
    public int CantidadDeUsuarios() {
        
        int i=0;
        
        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement("SELECT count(*) as cuantos FROM productoaseo");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                i = res.getInt("cuantos");
            }

            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El correo no existe");
            System.err.println(e.getMessage());
        }
        return i;
    }
    
    
     public String[][] Usuarios() {
        
        String Usuarios[][] = new String[CantidadDeUsuarios()][5];
        
        try {

            Connection cn = conexion();

            PreparedStatement pstm = cn.prepareStatement("SELECT nombre, cantidad , precio, olor, fecha_vencimiento FROM `productoaseo` ");
            //Se crea un objeto donde se almacena el resultado
            //Y con el comando executeQuery se ejecuta la consulta en la base de datos
            ResultSet res = pstm.executeQuery();
            //Recorre el resultado para mostrarlo en los jtf
            int i=0;
            while (res.next()) {
                //jTF_identificacion.setText(res.getString( "id_persona" ));
                Usuarios[i][0] = (res.getString("nombre"));
                Usuarios[i][1] = (String.valueOf(res.getInt("cantidad")));
                Usuarios[i][2] = (String.valueOf(res.getInt("cantidad")));
                Usuarios[i][3] = (res.getString("olor"));
                Usuarios[i][4] = (res.getString("fecha_vencimiento"));
                i++;
            }

            res.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El correo no existe");
            System.err.println(e.getMessage());
        }
        return Usuarios;
    }
}