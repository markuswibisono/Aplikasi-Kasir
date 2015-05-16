/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr_pae_2;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Markus
 */
public class panelPelanggan extends javax.swing.JPanel {

    JDialog dialog;
    String idPelanggan, idTransaksi;
    Connection connection;
    int totalHarga;
    Transaksi t;

    public panelPelanggan(JDialog dialog, String idPelanggan, int totalHarga, String idTransaksi, Transaksi t) {
        initComponents();
        this.dialog = dialog;
        this.idPelanggan = idPelanggan;
        this.totalHarga = totalHarga;
        this.idTransaksi = idTransaksi;
        this.t = t;
        koneksi();
        try {
            String sql = "SELECT Id_Pelanggan, Nama_Pelanggan, (SELECT SUM(Total_Harga_Transaksi) FROM utang WHERE Id_Pelanggan = '" + idPelanggan + "') FROM pelanggan WHERE Id_Pelanggan = '" + idPelanggan + "'";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            rs.next();
            lblIdMember.setText(rs.getString(1));
            lblNama.setText(rs.getString(2));
            lblTotalHutang.setText(rs.getInt(3) + "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        lblTransaksiSekarang.setText(totalHarga + "");
    }

    public void koneksi() {
        try {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setServerName("localhost");
            ds.setDatabaseName("db_sales");
            ds.setUser("root");
            ds.setPort(3306);
            connection = (Connection) ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getIdUtang() {
        try {
            String sql = "SELECT COUNT(*) + 1 FROM utang";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return "Utg" + rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rbYa = new javax.swing.JRadioButton();
        rbTidak = new javax.swing.JRadioButton();
        txtCicilan = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lblTotalHutang = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        lblIdMember = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTransaksiSekarang = new javax.swing.JLabel();

        jLabel1.setText("ID Member");

        jLabel2.setText("Nama");

        jLabel3.setText("Total Hutang sekarang");

        jLabel4.setText("Bayar Cicil");

        buttonGroup1.add(rbYa);
        rbYa.setText("Ya");
        rbYa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbYaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbTidak);
        rbTidak.setSelected(true);
        rbTidak.setText("Tidak");
        rbTidak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTidakActionPerformed(evt);
            }
        });

        txtCicilan.setEnabled(false);

        jButton1.setText("submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblTotalHutang.setText("Total Hutang sekarang");

        lblNama.setText("Nama");

        lblIdMember.setText("ID Member");

        jLabel5.setText("Transaksi sekarang");

        lblTransaksiSekarang.setText("Transaksi sekarang");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdMember)
                            .addComponent(lblNama)
                            .addComponent(lblTotalHutang))
                        .addContainerGap(207, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rbTidak)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbYa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCicilan, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(lblTransaksiSekarang)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(51, 51, 51))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblIdMember))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblNama))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblTotalHutang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblTransaksiSekarang))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(rbYa)
                    .addComponent(rbTidak)
                    .addComponent(txtCicilan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rbYaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbYaActionPerformed
        txtCicilan.setEnabled(true);
    }//GEN-LAST:event_rbYaActionPerformed

    private void rbTidakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTidakActionPerformed
        txtCicilan.setText("");
        txtCicilan.setEnabled(false);
    }//GEN-LAST:event_rbTidakActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int utang = 0;
        int cicilan = 5;
        if (rbTidak.isSelected()) {
            utang = totalHarga;
        } else {
            utang = totalHarga - Integer.parseInt(txtCicilan.getText());
            cicilan -= 1;
        }
        try {
            String sql = "INSERT INTO utang (Id_Utang, Id_Pelanggan, Id_Transaksi, Cicilan, Total_Harga_Transaksi, Sisa_kali_cicilan) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, getIdUtang());
            stm.setString(2, idPelanggan);
            stm.setString(3, idTransaksi);
            stm.setInt(4, 5);
            stm.setInt(5, utang);
            stm.setInt(6, cicilan);
            if (stm.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Cicilan Berhasil");
            }
            t.add_transaksi(true);
            dialog.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblIdMember;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblTotalHutang;
    private javax.swing.JLabel lblTransaksiSekarang;
    private javax.swing.JRadioButton rbTidak;
    private javax.swing.JRadioButton rbYa;
    private javax.swing.JTextField txtCicilan;
    // End of variables declaration//GEN-END:variables
}
