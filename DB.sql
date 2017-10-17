-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema BD_FV
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `BD_FV` ;

-- -----------------------------------------------------
-- Schema BD_FV
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BD_FV` DEFAULT CHARACTER SET utf8 ;
USE `BD_FV` ;

-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Ferreterias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Ferreterias` (
  `Fer_ID` INT NOT NULL AUTO_INCREMENT,
  `Fer_Nombre` VARCHAR(30) NOT NULL,
  `Fer_CedulaJurida` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`Fer_ID`))
ENGINE = InnoDB
COMMENT = 'Esta tabla se encarga de almacenar la informacion de la ferreteria.';


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Roles` (
  `Rol_ID` INT NOT NULL AUTO_INCREMENT,
  `Rol_Nombre` VARCHAR(30) NOT NULL,
  `Rol_Descripcion` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`Rol_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Personas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Personas` (
  `Per_Cedula` VARCHAR(30) NOT NULL,
  `Per_Nombre` VARCHAR(30) NOT NULL,
  `Per_SNombre` VARCHAR(30) NULL,
  `Per_PApellido` VARCHAR(30) NOT NULL,
  `Per_SApellido` VARCHAR(30) NULL,
  PRIMARY KEY (`Per_Cedula`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Usuarios` (
  `Usu_Persona` VARCHAR(30) NOT NULL,
  `Usu_Nombre` VARCHAR(10) NOT NULL,
  `Usu_Contraseña` VARCHAR(10) NOT NULL,
  `Usu_Rol` INT NULL,
  PRIMARY KEY (`Usu_Persona`),
  INDEX `idx_Usuario_Rol` (`Usu_Rol` ASC),
  UNIQUE INDEX `Usu_Nombre_UNIQUE` (`Usu_Nombre` ASC),
  CONSTRAINT `fk_tb_Usuario_tb_Rol1`
    FOREIGN KEY (`Usu_Rol`)
    REFERENCES `BD_FV`.`tb_Roles` (`Rol_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_Usuario_tb_Persona1`
    FOREIGN KEY (`Usu_Persona`)
    REFERENCES `BD_FV`.`tb_Personas` (`Per_Cedula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Esta tabla almacena la informacion de cada usuario';


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Permisos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Permisos` (
  `Per_ID` INT NOT NULL AUTO_INCREMENT,
  `Per_Nombre` VARCHAR(30) NOT NULL,
  `Per_Descripcion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Per_ID`))
ENGINE = InnoDB
COMMENT = 'Registra los permisos de accesso del sistema';


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Contactos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Contactos` (
  `Con_ID` INT NOT NULL AUTO_INCREMENT,
  `Con_Contacto` VARCHAR(30) NOT NULL,
  `Con_TipoContacto` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`Con_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Provincias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Provincias` (
  `Pro_ID` INT NOT NULL,
  `Pro_Nombre` VARCHAR(15) NOT NULL,
  `Pro_Descripcion` VARCHAR(45) NULL,
  PRIMARY KEY (`Pro_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Cantones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Cantones` (
  `Can_ID` INT NOT NULL,
  `Can_Nombre` VARCHAR(15) NOT NULL,
  `Can_Descripcion` VARCHAR(45) NULL,
  PRIMARY KEY (`Can_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Distritos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Distritos` (
  `Dis_ID` INT NOT NULL,
  `Dis_Nombre` VARCHAR(45) NOT NULL,
  `Dis_Descripcion` VARCHAR(45) NULL,
  PRIMARY KEY (`Dis_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Direcciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Direcciones` (
  `Dir_ID` INT NOT NULL AUTO_INCREMENT,
  `Dir_Provincia` INT NULL,
  `Dir_Canton` INT NULL,
  `Dir_Distrito` INT NULL,
  `Dir_DirExacta` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Dir_ID`),
  INDEX `idx_Direcciones_Provincia` (`Dir_Provincia` ASC),
  INDEX `idx_Direcciones_Canton` (`Dir_Canton` ASC),
  INDEX `idx_Direcciones_Distrito` (`Dir_Distrito` ASC),
  CONSTRAINT `fk_Direcciones_Provincia`
    FOREIGN KEY (`Dir_Provincia`)
    REFERENCES `BD_FV`.`tb_Provincias` (`Pro_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Direcciones_Canton`
    FOREIGN KEY (`Dir_Canton`)
    REFERENCES `BD_FV`.`tb_Cantones` (`Can_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Direcciones_Distrito`
    FOREIGN KEY (`Dir_Distrito`)
    REFERENCES `BD_FV`.`tb_Distritos` (`Dis_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_PermisosXRol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_PermisosXRol` (
  `RPR_Rol` INT NOT NULL,
  `RPR_Permiso` INT NOT NULL,
  PRIMARY KEY (`RPR_Rol`, `RPR_Permiso`),
  INDEX `idx_Rol_Permisos` (`RPR_Rol` ASC),
  INDEX `idx_Permisos_Rol` (`RPR_Permiso` ASC),
  CONSTRAINT `fk_Permisos_Rol`
    FOREIGN KEY (`RPR_Permiso`)
    REFERENCES `BD_FV`.`tb_Permisos` (`Per_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Rol_Permisos`
    FOREIGN KEY (`RPR_Rol`)
    REFERENCES `BD_FV`.`tb_Roles` (`Rol_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Personas_Contactos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Personas_Contactos` (
  `RPC_Persona` VARCHAR(30) NOT NULL,
  `RPC_Contacto` INT NOT NULL,
  PRIMARY KEY (`RPC_Persona`, `RPC_Contacto`),
  INDEX `idx_Contactos_Persona` (`RPC_Contacto` ASC),
  INDEX `idx_Persona_Contactos` (`RPC_Persona` ASC),
  CONSTRAINT `fk_Persona_Contactos`
    FOREIGN KEY (`RPC_Persona`)
    REFERENCES `BD_FV`.`tb_Personas` (`Per_Cedula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Contactos_Persona`
    FOREIGN KEY (`RPC_Contacto`)
    REFERENCES `BD_FV`.`tb_Contactos` (`Con_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Personas_Direcciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Personas_Direcciones` (
  `RPD_Persona` VARCHAR(30) NOT NULL,
  `RPD_Direccion` INT NOT NULL,
  PRIMARY KEY (`RPD_Persona`, `RPD_Direccion`),
  INDEX `idx_Direccione_Persona` (`RPD_Direccion` ASC),
  INDEX `idx_Persona_Direcciones` (`RPD_Persona` ASC),
  CONSTRAINT `fk_Persona_Direcciones`
    FOREIGN KEY (`RPD_Persona`)
    REFERENCES `BD_FV`.`tb_Personas` (`Per_Cedula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Direcciones_Persona`
    FOREIGN KEY (`RPD_Direccion`)
    REFERENCES `BD_FV`.`tb_Direcciones` (`Dir_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Ferreterias_Personas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Ferreterias_Personas` (
  `RFP_Ferreteria` INT NOT NULL,
  `RFP_Persona` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`RFP_Ferreteria`, `RFP_Persona`),
  INDEX `fk_tb_ferreteria_has_tb_Persona_tb_Persona1_idx` (`RFP_Persona` ASC),
  INDEX `fk_tb_ferreteria_has_tb_Persona_tb_ferreteria1_idx` (`RFP_Ferreteria` ASC),
  CONSTRAINT `fk_tb_ferreteria_has_tb_Persona_tb_ferreteria1`
    FOREIGN KEY (`RFP_Ferreteria`)
    REFERENCES `BD_FV`.`tb_Ferreterias` (`Fer_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_ferreteria_has_tb_Persona_tb_Persona1`
    FOREIGN KEY (`RFP_Persona`)
    REFERENCES `BD_FV`.`tb_Personas` (`Per_Cedula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Articulos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Articulos` (
  `Art_Codigo` VARCHAR(30) NOT NULL,
  `Art_Nombre` VARCHAR(30) NOT NULL,
  `Art_Descripcion` VARCHAR(200) NOT NULL,
  `Art_Marca` VARCHAR(30) NULL,
  `Art_UnidadMedida` VARCHAR(30) NULL,
  `Art_Precio` DECIMAL(8,2) NOT NULL,
  `Art_Descuento` DECIMAL(8,2) NULL,
  `Art_EstadoDescuento` VARCHAR(1) NULL,
  PRIMARY KEY (`Art_Codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Bodegas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Bodegas` (
  `Bod_Codigo` VARCHAR(15) NOT NULL,
  `Bod_Nombre` VARCHAR(30) NOT NULL,
  `Bob_Direccion` INT NOT NULL,
  `Bod_Descripcion` VARCHAR(150) NULL,
  `Bob_Ferreteria` INT NOT NULL,
  PRIMARY KEY (`Bod_Codigo`),
  INDEX `idx_Bodega_Direcciones1_idx` (`Bob_Direccion` ASC),
  INDEX `fk_tb_Bodega_tb_ferreteria1_idx` (`Bob_Ferreteria` ASC),
  CONSTRAINT `fk_Bodega_Direcciones`
    FOREIGN KEY (`Bob_Direccion`)
    REFERENCES `BD_FV`.`tb_Direcciones` (`Dir_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tb_Bodega_tb_ferreteria1`
    FOREIGN KEY (`Bob_Ferreteria`)
    REFERENCES `BD_FV`.`tb_Ferreterias` (`Fer_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Inventarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Inventarios` (
  `Inv_Codigo` VARCHAR(30) NOT NULL,
  `Inv_Articulo` VARCHAR(30) NOT NULL,
  `Inv_Fecha` DATE NOT NULL,
  `Inv_Bodega` VARCHAR(15) NULL,
  `Inv_Cantidad` INT NOT NULL,
  `Inv_Estado` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`Inv_Codigo`),
  INDEX `idx_Existencias_Articulos` (`Inv_Articulo` ASC),
  INDEX `idx_Inventario_Bodega` (`Inv_Bodega` ASC),
  CONSTRAINT `fk_Existencias_Articulos`
    FOREIGN KEY (`Inv_Articulo`)
    REFERENCES `BD_FV`.`tb_Articulos` (`Art_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inventario_Bodega`
    FOREIGN KEY (`Inv_Bodega`)
    REFERENCES `BD_FV`.`tb_Bodegas` (`Bod_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Proveedores` (
  `Pro_ID` INT NOT NULL AUTO_INCREMENT,
  `Pro_Nombre` VARCHAR(30) NOT NULL,
  `Pro_CedulaJuridica` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`Pro_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Compras`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Compras` (
  `Com_Numero` VARCHAR(30) NOT NULL,
  `Com_Proveedor` INT NOT NULL,
  `Com_Fecha` DATE NOT NULL,
  `Com_Nombre` VARCHAR(30) NULL,
  `Com_Total` DECIMAL(8,2) NOT NULL,
  `Com_Descuento` DECIMAL(8,2) NULL,
  `Com_ImpVenta` DECIMAL(8,2) NOT NULL,
  `Com_Flete` DECIMAL(8,2) NULL,
  `Com_FleteC` DECIMAL(8,2) NULL,
  `Com_ServCarga` DECIMAL(8,2) NOT NULL,
  `Com_SubTotal` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Com_Numero`),
  INDEX `idx_FactProveedor_Proveedores` (`Com_Proveedor` ASC),
  CONSTRAINT `fk_FactProveedor_Proveedores`
    FOREIGN KEY (`Com_Proveedor`)
    REFERENCES `BD_FV`.`tb_Proveedores` (`Pro_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_TiposPago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_TiposPago` (
  `Tip_Codigo` INT NOT NULL,
  `Tip_Nombre` VARCHAR(45) NOT NULL,
  `Tip_Descripcion` VARCHAR(45) NULL,
  PRIMARY KEY (`Tip_Codigo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Abonos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Abonos` (
  `Abo_Codigo` INT NOT NULL,
  `Abo_Fecha` DATE NOT NULL,
  `Abo_Monto` DECIMAL(8,2) NOT NULL,
  `Abo_TipoPago` INT NOT NULL,
  `Abo_NumeroDeposito` VARCHAR(30) NULL,
  `Abo_Estado` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`Abo_Codigo`),
  INDEX `idx_Abonos_Tipo_Pago` (`Abo_TipoPago` ASC),
  CONSTRAINT `fk_Abonos_Tipo_Pago`
    FOREIGN KEY (`Abo_TipoPago`)
    REFERENCES `BD_FV`.`tb_TiposPago` (`Tip_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_ArticulosXCompra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_ArticulosXCompra` (
  `Art_ID` INT NOT NULL AUTO_INCREMENT,
  `Art_Compra` VARCHAR(30) NOT NULL,
  `Art_Articulo` VARCHAR(30) NOT NULL,
  `Art_Precio` DECIMAL(8,2) NOT NULL,
  `Art_Cantidad` INT NOT NULL,
  INDEX `idx_Articulos_FactProveedor` (`Art_Articulo` ASC),
  INDEX `idx_FactProveedor_Articulos` (`Art_Compra` ASC),
  PRIMARY KEY (`Art_ID`),
  CONSTRAINT `fk_FactProveedor_Articulos`
    FOREIGN KEY (`Art_Compra`)
    REFERENCES `BD_FV`.`tb_Compras` (`Com_Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Articulos_FactProveedor`
    FOREIGN KEY (`Art_Articulo`)
    REFERENCES `BD_FV`.`tb_Articulos` (`Art_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Clientes` (
  `Cli_Persona` VARCHAR(30) NOT NULL,
  `Cli_FechaIngreso` DATE NOT NULL,
  `Cli_Estado` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`Cli_Persona`),
  CONSTRAINT `fk_tb_Cliente_tb_Persona1`
    FOREIGN KEY (`Cli_Persona`)
    REFERENCES `BD_FV`.`tb_Personas` (`Per_Cedula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Direcciones_Proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Direcciones_Proveedores` (
  `RDP_Direccion` INT NOT NULL,
  `RDP_Proveedor` INT NOT NULL,
  PRIMARY KEY (`RDP_Direccion`, `RDP_Proveedor`),
  INDEX `idx_Proveedores_Direcciones` (`RDP_Proveedor` ASC),
  INDEX `idx_Direcciones_Proveedores` (`RDP_Direccion` ASC),
  CONSTRAINT `fk_Direcciones_Proveedores`
    FOREIGN KEY (`RDP_Direccion`)
    REFERENCES `BD_FV`.`tb_Direcciones` (`Dir_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk__Proveedores_Direcciones`
    FOREIGN KEY (`RDP_Proveedor`)
    REFERENCES `BD_FV`.`tb_Proveedores` (`Pro_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Contactos_Proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Contactos_Proveedores` (
  `RCP_Contacto` INT NOT NULL,
  `RCP_Proveedor` INT NOT NULL,
  PRIMARY KEY (`RCP_Contacto`, `RCP_Proveedor`),
  INDEX `idx_Contactos_Proveedores` (`RCP_Proveedor` ASC),
  INDEX `idx_Proveedores_Proveedores` (`RCP_Contacto` ASC),
  CONSTRAINT `fk_Contactos_Proveedores`
    FOREIGN KEY (`RCP_Contacto`)
    REFERENCES `BD_FV`.`tb_Contactos` (`Con_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Proveedores_Contactos`
    FOREIGN KEY (`RCP_Proveedor`)
    REFERENCES `BD_FV`.`tb_Proveedores` (`Pro_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Facturas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Facturas` (
  `Fac_Codigo` INT NOT NULL,
  `Fac_Cliente` VARCHAR(30) NOT NULL,
  `Fac_Fecha` DATE NOT NULL,
  `Fat_Subtotal` DECIMAL(8,2) NOT NULL,
  `Fac_Descuento` DECIMAL(8,2) NOT NULL,
  `Fac_Total` DECIMAL(8,2) NOT NULL,
  `Fac_ImpVentas` DECIMAL(8,2) NOT NULL,
  `Fac_Estado` VARCHAR(1) NOT NULL,
  `Fact_TipoFact` VARCHAR(1) NOT NULL COMMENT 'Tipo de factura (Contado o credito)',
  PRIMARY KEY (`Fac_Codigo`),
  INDEX `idx_Factur_Cliente` (`Fac_Cliente` ASC),
  CONSTRAINT `fk_Facturacion_Cliente`
    FOREIGN KEY (`Fac_Cliente`)
    REFERENCES `BD_FV`.`tb_Clientes` (`Cli_Persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_NotasCredito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_NotasCredito` (
  `Not_Codigo` INT NOT NULL AUTO_INCREMENT,
  `Not_Factura` VARCHAR(30) NOT NULL,
  `Not_Fecha` DATE NOT NULL,
  `Not_Monto` DECIMAL(8,2) NOT NULL,
  `Not_Justificacion` VARCHAR(200) NULL,
  PRIMARY KEY (`Not_Codigo`),
  INDEX `idx_NotaCredito_Facturacion` (`Not_Factura` ASC),
  CONSTRAINT `fk_NotaCredito_Facturacion`
    FOREIGN KEY (`Not_Factura`)
    REFERENCES `BD_FV`.`tb_Facturas` (`Fac_Cliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Pagos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Pagos` (
  `Pag_Codigo` INT NOT NULL,
  `Pag_Factura` INT NOT NULL,
  `Pag_TipoPago` INT NOT NULL,
  `Pag_NotaCredito` INT NULL,
  `Pag_Monto` DECIMAL(8,2) NULL,
  `Pag_Estado` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`Pag_Codigo`),
  INDEX `idx_Pagos_Tipo_Pago` (`Pag_TipoPago` ASC),
  INDEX `idx_Pagos_Facturacion` (`Pag_Factura` ASC),
  INDEX `idx_Pagos_NotaCredito` (`Pag_NotaCredito` ASC),
  CONSTRAINT `fk_Pagos_Tipo_Pago`
    FOREIGN KEY (`Pag_TipoPago`)
    REFERENCES `BD_FV`.`tb_TiposPago` (`Tip_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pagos_facturacion`
    FOREIGN KEY (`Pag_Factura`)
    REFERENCES `BD_FV`.`tb_Facturas` (`Fac_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pagos_NotaCredito`
    FOREIGN KEY (`Pag_NotaCredito`)
    REFERENCES `BD_FV`.`tb_NotasCredito` (`Not_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_CuentasXCobrar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_CuentasXCobrar` (
  `Cue_Codigo` INT NOT NULL,
  `Cue_Factura` VARCHAR(30) NOT NULL,
  `Cue_Cliente` VARCHAR(30) NOT NULL,
  `Cue_Saldo` DECIMAL(8,2) NOT NULL,
  `Cue_SaldoFac` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`Cue_Codigo`),
  INDEX `idx_CuntXCobr_Facturacion` (`Cue_Factura` ASC),
  INDEX `idx_CuntXCobr_Cliente` (`Cue_Cliente` ASC),
  CONSTRAINT `fk_CuntXCobr_Facturacion`
    FOREIGN KEY (`Cue_Factura`)
    REFERENCES `BD_FV`.`tb_Facturas` (`Fac_Cliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CuntXCob_Cliente`
    FOREIGN KEY (`Cue_Cliente`)
    REFERENCES `BD_FV`.`tb_Clientes` (`Cli_Persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_CxC_Abonos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_CxC_Abonos` (
  `RCA_CuentaCobrar` INT NOT NULL,
  `RCA_Abono` INT NOT NULL,
  PRIMARY KEY (`RCA_CuentaCobrar`, `RCA_Abono`),
  INDEX `idx_Abonos_CuntasXCobrar` (`RCA_Abono` ASC),
  INDEX `idx_CuntasXCobrar_Abonos` (`RCA_CuentaCobrar` ASC),
  CONSTRAINT `fk_CuntasXCobrar_Abonos`
    FOREIGN KEY (`RCA_CuentaCobrar`)
    REFERENCES `BD_FV`.`tb_CuentasXCobrar` (`Cue_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Abonos_CuentasXCobrar`
    FOREIGN KEY (`RCA_Abono`)
    REFERENCES `BD_FV`.`tb_Abonos` (`Abo_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_ArticulosXFactura`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_ArticulosXFactura` (
  `Art_ID` INT NOT NULL AUTO_INCREMENT,
  `Art_Factura` INT NOT NULL,
  `Art_Articulo` VARCHAR(30) NOT NULL,
  `Art_Precio` DECIMAL(8,2) NOT NULL,
  `Art_Cantidad` INT NOT NULL,
  INDEX `idx_Articulo_Facturacion` (`Art_Articulo` ASC),
  INDEX `idx_Facturacion_Articulo` (`Art_Factura` ASC),
  PRIMARY KEY (`Art_ID`),
  CONSTRAINT `fk_Articulo_Facturacion`
    FOREIGN KEY (`Art_Factura`)
    REFERENCES `BD_FV`.`tb_Facturas` (`Fac_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Fk_Facturacion_Articulo`
    FOREIGN KEY (`Art_Articulo`)
    REFERENCES `BD_FV`.`tb_Articulos` (`Art_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_Detalles_Inventario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_Detalles_Inventario` (
  `Det_Codigo` INT NOT NULL,
  `Det_Fecha` DATE NOT NULL,
  `Det_Cantidad` INT NOT NULL,
  `Det_Precio` DECIMAL(8,2) NOT NULL,
  `Det_Inventario` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`Det_Codigo`),
  INDEX `fk_DetInventario__Inventario` (`Det_Inventario` ASC),
  CONSTRAINT `fk_DetInventario_Inventario`
    FOREIGN KEY (`Det_Inventario`)
    REFERENCES `BD_FV`.`tb_Inventarios` (`Inv_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_CuentasXPagar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_CuentasXPagar` (
  `Cue_Codigo` INT NOT NULL AUTO_INCREMENT,
  `Cue_Compra` VARCHAR(30) NOT NULL,
  `Cue_Proveedor` INT NOT NULL,
  `Cue_Saldo` DECIMAL(8,2) NULL COMMENT 'Saldo por paga',
  `Cue_SaldoCompra` DECIMAL(8,2) NOT NULL COMMENT 'Saldo total de la compra',
  PRIMARY KEY (`Cue_Codigo`),
  INDEX `idx_CuentasXPagar_Proveedores` (`Cue_Proveedor` ASC),
  INDEX `idx_CuentasXPagar_Compras` (`Cue_Compra` ASC),
  CONSTRAINT `fk_CuentasXPagar_Proveedores`
    FOREIGN KEY (`Cue_Proveedor`)
    REFERENCES `BD_FV`.`tb_Proveedores` (`Pro_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CuentasXPagar_Compras`
    FOREIGN KEY (`Cue_Compra`)
    REFERENCES `BD_FV`.`tb_Compras` (`Com_Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BD_FV`.`tb_CxP_Abonos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BD_FV`.`tb_CxP_Abonos` (
  `RCA_CuentaPagar` INT NOT NULL,
  `RCA_Abono` INT NOT NULL,
  PRIMARY KEY (`RCA_CuentaPagar`, `RCA_Abono`),
  INDEX `idx_Abonos_CuentasXPagar` (`RCA_CuentaPagar` ASC),
  INDEX `idx_CuentasXPagar_Abonos` (`RCA_Abono` ASC),
  CONSTRAINT `fk_Abonos_CuentasXPagar`
    FOREIGN KEY (`RCA_Abono`)
    REFERENCES `BD_FV`.`tb_Abonos` (`Abo_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CuentasXPagar_Abonos`
    FOREIGN KEY (`RCA_CuentaPagar`)
    REFERENCES `BD_FV`.`tb_CuentasXPagar` (`Cue_Codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
