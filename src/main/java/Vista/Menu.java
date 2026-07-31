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

        btnrclientes = new javax.swing.JButton();
        btnturnos = new javax.swing.JButton();
        btnpagos = new javax.swing.JButton();
        panelmenu = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnrclientes.setText("Registro de Clientes");
        btnrclientes.addActionListener(this::btnrclientesActionPerformed);

        btnturnos.setText("Turnos");
        btnturnos.addActionListener(this::btnturnosActionPerformed);

        btnpagos.setText("Pagos");

        javax.swing.GroupLayout panelmenuLayout = new javax.swing.GroupLayout(panelmenu);
        panelmenu.setLayout(panelmenuLayout);
        panelmenuLayout.setHorizontalGroup(
            panelmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        panelmenuLayout.setVerticalGroup(
            panelmenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 195, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnrclientes, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnturnos, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnpagos, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addComponent(panelmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnrclientes)
                        .addGap(48, 48, 48)
                        .addComponent(btnturnos)
                        .addGap(55, 55, 55)
                        .addComponent(btnpagos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnrclientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrclientesActionPerformed
      vistaRegistroCliente.setVisible(true);
      vistaRegistroCliente.setLocationRelativeTo(null);
      this.dispose();
    }//GEN-LAST:event_btnrclientesActionPerformed

    private void btnturnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnturnosActionPerformed
      vistaRegistroMascota.setVisible(true);
      vistaRegistroMascota.setLocationRelativeTo(null);
      this.dispose();
    }//GEN-LAST:event_btnturnosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnpagos;
    private javax.swing.JButton btnrclientes;
    private javax.swing.JButton btnturnos;
    private javax.swing.JPanel panelmenu;
    // End of variables declaration//GEN-END:variables
}
