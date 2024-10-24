import java.sql.*;
import java.util.Scanner;

public class ConexionMySQL {
    // Configuración de la conexión

    public static void main(String[] args) {
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRes = null;

        try {
            myConn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project",
                    "root",
                    ""
            );
            System.out.println("Genial, nos conectamos");

            //Declaracion del scanner y de la opcion
            Scanner scanner = new Scanner(System.in);
            int option;

            do {
                System.out.println("\n----- CRUD 723 -----");
                System.out.println("1. Insertar Empleados");
                System.out.println("2. Consultar Empleados");
                System.out.println("3. Actualizar Empleado");
                System.out.println("4. Eliminar Empleado");
                System.out.println("5. Salir");
                System.out.println("Ingrese la opcion");
                option = scanner.nextInt();

                switch (option){
                    case 1:

                        //Insertar Empleados

                        System.out.println("Nombre: ");
                        String nombre = scanner.next();
                        System.out.println("Apellido: ");
                        String apellido = scanner.next();
                        System.out.println("Cargo: ");
                        String cargo = scanner.next();
                        System.out.println("Email: ");
                        String email = scanner.next();
                        System.out.println("Salario: ");
                        Double salario = scanner.nextDouble();
                        insertarEmpleado(myConn , nombre, apellido, cargo, email,salario);
                        break;

                    case 2:
                        //Consultar Empleados
                        consultarEmpleados(myConn);
                        break;

                    case 3:
                        System.out.println("id: ");
                        int nextid = scanner.nextInt();
                        System.out.println("nombre: ");
                        String nextnombre = scanner.next();
                        System.out.println("apellido: ");
                        String nextapellido = scanner.next();
                        System.out.println("cargo: ");
                        String nextCargo = scanner.next();
                        System.out.println("email: ");
                        String nextEmail  = scanner.next();
                        System.out.println("salario: ");
                        double nextSalario = scanner.nextDouble();
                        actualizarEmpleado(myConn, nextid, nextnombre, nextapellido, nextCargo, nextEmail, nextSalario);
                        //Actualizar
                        break;

                    case 4:
                        //Eliminar
                        break;

                    case 5:
                        //Salir
                        break;

                    default:

                        break;


                }

            }while (option != 5);


            // realizamos una consulta a la base de datos
            myStmt = myConn.createStatement();
            myRes = myStmt.executeQuery("SELECT * FROM employees");

            // iteramos los resultados para imprimir en consola.
            while (myRes.next()){
                System.out.println(myRes.getString("nombre") + " " + myRes.getString("apellido") );

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Algo salio mal :(");
        }
    }


    //Menu del crud



    //METODOS SE INSERCCION


    // Método para insertar un empleado
    private static void insertarEmpleado(Connection conexion, String nombre, String apellido, String cargo, String email, double salario)
            throws SQLException {
        String sql = "INSERT INTO employees (nombre, apellido, cargo, email, salario) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, cargo);
            pstmt.setString(4, email);
            pstmt.setDouble(5, salario);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }


    // Método para consultar empleados
    private static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s %s, Cargo: %s,Email: %s, Salario: %.2f%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("cargo"),
                        rs.getString("email"),
                        rs.getDouble("salario"));
            }
        }
    }

    // Método para actualizar un empleado
    private static void actualizarEmpleado(Connection conexion, int id, String nombre, String apellido, String cargo, String email, double salario)
            throws SQLException {
        String sql = "UPDATE employees SET nombre = ?, apellido = ?, cargo = ?, email = ?, salario = ?, WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, cargo);
            pstmt.setString(4, email);
            pstmt.setDouble(5, salario);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    // Método para eliminar un empleado
    private static void eliminarEmpleado(Connection conexion, int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }
}