package Vista;

public class Menu extends javax.swing.JFrame {
    Carga_DatosCli vistaRegistroCliente = new Carga_DatosCli();
    Carga_DatosMasc vistaRegistroMascota = new Carga_DatosMasc();
    
    public Menu() {
        initComponents();
    }
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTurnos = new javax.swing.JButton();
        btnPagos = new javax.swing.JButton();
        btnRegistrarServicios = new javax.swing.JButton();
        btnRegistrarMascota = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnRegistrarClientes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnTurnos.setText("Turnos");
        btnTurnos.addActionListener(this::btnTurnosActionPerformed);

        btnPagos.setText("Pagos");
        btnPagos.addActionListener(this::btnPagosActionPerformed);

        btnRegistrarServicios.setText("Registrar Servicios");
        btnRegistrarServicios.addActionListener(this::btnRegistrarServiciosActionPerformed);

        btnRegistrarMascota.setText("Registrar Mascota");
        btnRegistrarMascota.addActionListener(this::btnRegistrarMascotaActionPerformed);

        jLabel1.setText("Menú Principal");

        btnRegistrarClientes.setText("Registro de Clientes");
        btnRegistrarClientes.addActionListener(this::btnRegistrarClientesActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnRegistrarServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRegistrarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(346, 346, 346)
                            .addComponent(btnRegistrarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(379, 379, 379)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(388, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(51, 51, 51)
                .addComponent(btnRegistrarClientes)
                .addGap(25, 25, 25)
                .addComponent(btnRegistrarMascota)
                .addGap(26, 26, 26)
                .addComponent(btnTurnos)
                .addGap(18, 18, 18)
                .addComponent(btnPagos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnRegistrarServicios)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarClientesActionPerformed
      vistaRegistroCliente.setVisible(true);
      vistaRegistroCliente.setLocationRelativeTo(null);
      this.dispose();
    }//GEN-LAST:event_btnRegistrarClientesActionPerformed

    private void btnTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurnosActionPerformed
      Historial_Turno turno = new Historial_Turno();
      turno.setVisible(true);
      turno.setLocationRelativeTo(null);
      dispose();
    }//GEN-LAST:event_btnTurnosActionPerformed

    private void btnRegistrarServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarServiciosActionPerformed
        // TODO add your handling code here:
        
       Carga_DatosServ servicios = new Carga_DatosServ();
       servicios.setVisible(true);
       servicios.setLocationRelativeTo(null);
       dispose();
    }//GEN-LAST:event_btnRegistrarServiciosActionPerformed

    private void btnPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagosActionPerformed
        // TODO add your handling code here:
        
        Registro_Pagos pagos = new Registro_Pagos();
        pagos.setVisible(true);
        pagos.setLocationRelativeTo(null);
        dispose();
    }//GEN-LAST:event_btnPagosActionPerformed

    private void btnRegistrarMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarMascotaActionPerformed
        // TODO add your handling code here:
        
        Carga_DatosMasc mascotas = new Carga_DatosMasc();
        mascotas.setVisible(true);
        mascotas.setLocationRelativeTo(null);

        dispose();
    }//GEN-LAST:event_btnRegistrarMascotaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPagos;
    private javax.swing.JButton btnRegistrarClientes;
    private javax.swing.JButton btnRegistrarMascota;
    private javax.swing.JButton btnRegistrarServicios;
    private javax.swing.JButton btnTurnos;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
