SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE `bd_fv`.`tb_personas_direcciones`;
TRUNCATE `bd_fv`.`tb_personas_contactos`;
TRUNCATE `bd_fv`.`tb_permisos_usuarios`;
TRUNCATE `bd_fv`.`tb_direcciones`;
TRUNCATE `bd_fv`.`tb_contactos`;
TRUNCATE `bd_fv`.`tb_permisos`;
TRUNCATE `bd_fv`.`tb_clientes`;
TRUNCATE `bd_fv`.`tb_usuarios`;
TRUNCATE `bd_fv`.`tb_personas`;
SET FOREIGN_KEY_CHECKS = 1; 

ALTER TABLE `bd_fv`.`tb_contactos` AUTO_INCREMENT=0;
ALTER TABLE `bd_fv`.`tb_direcciones` AUTO_INCREMENT=0;
ALTER TABLE `bd_fv`.`tb_permisos` AUTO_INCREMENT=0;

INSERT INTO `bd_fv`.`tb_personas` (`Per_Cedula`, `Per_Nombre`, `Per_SNombre`, `Per_PApellido`, `Per_SApellido`, `Per_Estado`) VALUES ('root', 'root', 'root', 'root', 'root', 'A');
INSERT INTO `bd_fv`.`tb_usuarios` (`Usu_Persona`, `Usu_Nombre`, `Usu_Contraseña`, `Usu_Estado`) VALUES ('root', 'root', 'root', 'A');
INSERT INTO `bd_fv`.`tb_clientes` (`Cli_Persona`, `Cli_FechaIngreso`, `Cli_Estado`) VALUES ('root', '2017-11-06', 'A');


INSERT INTO `bd_fv`.`tb_contactos` (`Con_ID`, `Con_Contacto`, `Con_TipoContacto`) VALUES ('1', 'root', 'TEL');
INSERT INTO `bd_fv`.`tb_contactos` (`Con_ID`, `Con_Contacto`, `Con_TipoContacto`) VALUES ('2', 'root@root.com', 'EMAIL');
INSERT INTO `bd_fv`.`tb_contactos` (`Con_ID`, `Con_Contacto`, `Con_TipoContacto`) VALUES ('3', 'root', 'TEL2');

INSERT INTO `bd_fv`.`tb_direcciones` (`Dir_ID`, `Dir_DirExacta`) VALUES ('1', 'root');

INSERT INTO `bd_fv`.`tb_personas_contactos` (`RPC_Persona`, `RPC_Contacto`) VALUES ('root', '1');
INSERT INTO `bd_fv`.`tb_personas_contactos` (`RPC_Persona`, `RPC_Contacto`) VALUES ('root', '2');
INSERT INTO `bd_fv`.`tb_personas_contactos` (`RPC_Persona`, `RPC_Contacto`) VALUES ('root', '3');

INSERT INTO `bd_fv`.`tb_personas_direcciones` (`RPD_Persona`, `RPD_Direccion`) VALUES ('root', '1');

INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('1', 'VER_FACTURACION', 'MENU', 'Entrar a Facturacion');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('2', 'VER_ABONOS', 'MENU', 'Entrar a Abonos');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('3', 'VER_NOTACREDITO', 'MENU', 'Entrar a Nota de Creditos');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('4', 'VER_INVENTARIO', 'MENU', 'Entrar a Inventario');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('5', 'VER_CLIENTES', 'MENU', 'Entrar a Clients');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('6', 'VER_EMPLEADOS', 'MENU', 'Entrar a Empleados');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('7', 'VER_PROVEEDORES', 'MENU', 'Entrar a Proveedores');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('8', 'VER_ANULACIONES', 'MENU', 'Entrar a Anulaciones');

INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('9', 'AGR_EMPLEADO', 'EMPLEADO', 'Agregar Empleados');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('10', 'BUS_EMPLEADO', 'EMPLEADO', 'Buscar Empleados');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('11', 'EDI_EMPLEADO', 'EMPLEADO', 'Editar Empleados');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('12', 'ELI_EMPLEADO', 'EMPLEADO', 'Eliminar Empleados');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('13', 'PER_EMPLEADO', 'EMPLEADO', 'Editar Permisos Empleado');


INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('14', 'AGR_CLIENTE', 'CLIENTE', 'Agregar Clientes');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('15', 'BUS_CLIENTE', 'CLIENTE', 'Buscar Clientes');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('16', 'EDI_CLIENTE', 'CLIENTE', 'Editar Clientes');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('17', 'ELI_CLIENTE', 'CLIENTE', 'Eliminar Clientes');

INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('18', 'AGR_PRODUCTO', 'PRODUCTO', 'Agregar Producto');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('19', 'BUS_PRODUCTO', 'PRODUCTO', 'Buscar Producto');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('20', 'EDI_PRODUCTO', 'PRODUCTO', 'Editar Producto');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('21', 'ELI_PRODUCTO', 'PRODUCTO', 'Eliminar Producto');

INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('22', 'AGR_INVENTARIO', 'INVENTARIO', 'Agregar Inventario');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('23', 'BUS_INVENTARIO', 'INVENTARIO', 'Buscar Inventario');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('24', 'EDI_INVENTARIO', 'INVENTARIO', 'Editar Inventario');
INSERT INTO `bd_fv`.`tb_permisos` (`Per_ID`, `Per_Nombre`, `Per_Modulo`, `Per_Descripcion`) VALUES ('25', 'ELI_INVENTARIO', 'INVENTARIO', 'Eliminar Inventario');

INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('1', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('2', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('3', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('4', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('5', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('6', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('7', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('8', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('9', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('10', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('11', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('12', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('13', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('14', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('15', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('16', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('17', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('18', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('19', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('20', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('21', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('22', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('23', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('24', 'root');
INSERT INTO `bd_fv`.`tb_permisos_usuarios` (`RPU_Permisos`, `RPU_Usuarios`) VALUES ('25', 'root');
