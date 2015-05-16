/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tr_pae_2;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Markus
 */
public class Transaksi extends javax.swing.JFrame {

    /**
     * Creates new form Transaksi
     */
    Connection connection;
    List<Object[]> record = new ArrayList<Object[]>();
    GetSetTrans gsTrans = new GetSetTrans();
    int totalHarga = 0;
    JDialog dialog;

    private Transaksi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void add_transaksi(boolean member) {
        try {
            int a = 0;
            for (int i = 0; i <= (table_transaksi.getRowCount() - 1); i++) {
                String sql = "INSERT INTO transaksi (No_Trans, Id_Transaksi, "
                        + "Id_Barang, Id_Pelanggan, User_name, Id_Toko, QTY_Barang, Amount, Tanggal) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE())";
                PreparedStatement stm = connection.prepareStatement(sql);

                //No_Trans
                int no_trans = gsTrans.getTampung_no() + (i + 1);
                stm.setInt(1, no_trans);

                //Id_Transaksi
                String id_trans = "Trans" + (gsTrans.getTampung_id_trans() + 1);
                stm.setString(2, id_trans);

                //Id_barang
                String id_barang = table_transaksi.getValueAt(i, 0).toString();
                stm.setString(3, id_barang);

                //Id_pelanggan sek sek
                String id_pelanggan = "";
                if (member) {
                    id_pelanggan = txtIdMember.getText();
                } else {
                    id_pelanggan = "0";
                }
                stm.setString(4, id_pelanggan);

                //User_name
                String username = gsTrans.getUsername();
                stm.setString(5, username);

                //Id_toko
                String id_toko = cbox_toko.getSelectedItem().toString();
                stm.setString(6, id_toko);

                //qty
                int qty = Integer.parseInt(table_transaksi.getValueAt(i, 3).toString());
                //int qty = 9;
                stm.setInt(7, qty);

                //amount
                int amount = Integer.parseInt(table_transaksi.getValueAt(i, 4).toString().toString());
                stm.setInt(8, amount);

                if (stm.executeUpdate() > 0) {
                    a = a + 1;
                }
                System.out.println(a + " nilai a ke " + i);
            }

            if (a > 0) {
                JOptionPane.showMessageDialog(this, "Coba sukses");
                String header[] = {"ID Barang", "Nama Barang", "Harga", "Banyak", "Total Harga"};
                DefaultTableModel model = new DefaultTableModel(null, header);
                table_transaksi.setModel(model);
                record.clear();
                gsTrans.setTampung_no(gsTrans.getTampung_no() + a);
                gsTrans.setTampung_id_trans(gsTrans.getTampung_id_trans() + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtIdMember.setText("");
        txtIdMember.setEnabled(false);
        rbTidak.setSelected(true);
        lblTotalHarga.setText("Total Harga : 0");
        text_bayar.setText("");
    }

    private void load_cbox_toko() {
        try {
            String sql = "SELECT Nama_Toko FROM toko";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            List<String> record = new ArrayList<String>();
            while (rs.next()) {
                record.add(rs.getString(1));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(record.toArray());
            cbox_toko.setModel(model);
        } catch (Exception e) {
        }
    }

    private void tampung_banyak_no() throws SQLException {
        String sql = "SELECT no_trans FROM transaksi";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        int tampung = 0;
        while (rs.next()) {
            tampung++;
        }
        System.out.println(tampung + " no trans");
        gsTrans.setTampung_no(tampung);
    }

    private void tampung_banyak_id_trans() throws SQLException {
        String sql = "SELECT DISTINCT id_transaksi FROM transaksi";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        int tampung = 0;
        while (rs.next()) {
            tampung++;
        }
        System.out.println(tampung + " id_trans");
        gsTrans.setTampung_id_trans(tampung);
    }

    public Transaksi(String Username) throws SQLException {
        initComponents();
        koneksi();
        load_cbox_toko();
        tampung_banyak_no();
        tampung_banyak_id_trans();
        table_barang.getTableHeader().setReorderingAllowed(false);
        table_transaksi.getTableHeader().setReorderingAllowed(false);

        //gawe tabel barang ngen ora iso di klik lebih dari 1 kali
        table_barang.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    table_barang.getCellEditor().stopCellEditing();
                }
            }
        });
        table_transaksi.setEnabled(false);
        gsTrans.setUsername(Username);
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

    public void setTotalHarga() {
        totalHarga = 0;
        for (int i = 0; i < table_transaksi.getRowCount(); i++) {
            totalHarga += Integer.parseInt(table_transaksi.getValueAt(i, 4).toString());
        }
        lblTotalHarga.setText("Total Harga : " + totalHarga + "");
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_transaksi = new javax.swing.JTable();
        btn_proses = new javax.swing.JButton();
        txt_nama = new javax.swing.JTextField();
        txt_banyak = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_barang = new javax.swing.JTable();
        cbox_toko = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        text_bayar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblTotalHarga = new javax.swing.JLabel();
        lblTotalHarga1 = new javax.swing.JLabel();
        rbYa = new javax.swing.JRadioButton();
        rbTidak = new javax.swing.JRadioButton();
        txtIdMember = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Transaksi Pembelian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        table_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Barang", "Nama Barang", "Harga", "Banyak", "Total Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_transaksi);

        btn_proses.setText("Process it");
        btn_proses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prosesActionPerformed(evt);
            }
        });

        txt_nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_namaKeyReleased(evt);
            }
        });

        txt_banyak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_banyakKeyReleased(evt);
            }
        });

        jLabel1.setText("Nama Barang :");

        jLabel2.setText("Banyak :");

        table_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Barang", "Nama Barang", "Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_barangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_barang);

        jButton1.setText("Bayar Cicilan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        text_bayar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Bayar Langsung : ");

        lblTotalHarga.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalHarga.setText("Total Harga : 0");

        lblTotalHarga1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTotalHarga1.setText("Member");

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

        txtIdMember.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIdMember.setEnabled(false);

        btnSubmit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSubmit.setText("Nyicil ?");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nama Toko : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 694, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cbox_toko, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblTotalHarga1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbTidak))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(rbYa)
                                .addGap(182, 182, 182)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_proses))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_banyak, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdMember, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_banyak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalHarga)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSubmit)
                        .addComponent(txtIdMember, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbTidak)
                        .addComponent(rbYa)
                        .addComponent(lblTotalHarga1)))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(text_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbox_toko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btn_proses))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_prosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prosesActionPerformed
//        int byar = Integer.parseInt(text_bayar.getText().toString());
//        if (byar < totalHarga) {
//            int ok = JOptionPane.showConfirmDialog(this, "Meh Nyicil?", "Confirm", JOptionPane.YES_NO_OPTION);
//            if (ok == JOptionPane.YES_OPTION) {
//                int ok1 = JOptionPane.showConfirmDialog(this, "Ada Kartu Member?", "Confirm", JOptionPane.YES_NO_OPTION);
//                if (ok1 == JOptionPane.YES_OPTION) {
//                    gsTrans.setId_trans("Trans" + (gsTrans.getTampung_id_trans() + 1));
//                    Utang u = new Utang(this, true, totalHarga, gsTrans.getId_trans());
//                    gsTrans.setTampung_id_trans(gsTrans.getTampung_id_trans() + 1);
//                    u.setVisible(true);
//                } else {
//                    int ok2 = JOptionPane.showConfirmDialog(this, "Ingin Bikin Member?", "Confirm", JOptionPane.YES_NO_OPTION);
//                    if (ok2 == JOptionPane.YES_OPTION) {
//                        Insert_dataMember add = new Insert_dataMember(this, true);
//                        add.setVisible(true);
//                        JOptionPane.showMessageDialog(this, "Selamat Anda Telah Terdaftar !! \nSilahkan lanjut transaksi ^^ ");
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Maaf anda tidak bisa ngutang");
//                    }
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Maaf Uang anda kurang");
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Uang kembalian Anda : " + totalHarga);
//            add_transaksi(false);
//        }




        if (text_bayar.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Maaf Inputan anda Kosong");
        } else {
            int byar = Integer.parseInt(text_bayar.getText().toString());
            if (table_transaksi.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "Maaf anda harus transaksi terlebih dahulu");
            } else if (byar < totalHarga) {
                JOptionPane.showMessageDialog(this, "Maaf Uang anda kurang");
            } else {
                JOptionPane.showMessageDialog(this, "Uang kembalian Anda : " + (Integer.parseInt(text_bayar.getText()) - totalHarga));
                add_transaksi(false);
            }
        }
    }//GEN-LAST:event_btn_prosesActionPerformed

    private void load_barang(String cari) {
        try {
            if (cari.equals("")) {
                cari = "&#$#";
            }

            String sql = "SELECT Id_Barang, Nama_Barang, Harga FROM barang WHERE Nama_Barang LIKE '%" + cari + "%'";

            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            List<Object[]> record = new ArrayList<Object[]>();
            while (rs.next()) {
                Object data[] = new Object[3];
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                int harga = rs.getInt(3);
                data[2] = harga;
                record.add(data);
            }
            Object tampung[][] = new Object[record.size()][3];
            int index = 0;
            for (Object[] ambil : record) {
                tampung[index] = ambil;
                index++;
            }
            String header[] = {"ID Barang", "Nama Barang", "Harga"};
            DefaultTableModel model = new DefaultTableModel(tampung, header);
            table_barang.setModel(model);
        } catch (Exception e) {
        }
    }

    private void txt_namaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namaKeyReleased
        String cari = txt_nama.getText().toString();
        load_barang(cari);
    }//GEN-LAST:event_txt_namaKeyReleased

    private void table_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_barangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_barangMouseClicked

    private void txt_banyakKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_banyakKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && table_barang.getSelectedRow() >= 0) {
            Object data[] = new Object[5];
            data[0] = table_barang.getValueAt(table_barang.getSelectedRow(), 0).toString();
            data[1] = table_barang.getValueAt(table_barang.getSelectedRow(), 1).toString();
            int harga = Integer.parseInt(table_barang.getValueAt(table_barang.getSelectedRow(), 2).toString());
            data[2] = harga;
            int banyak = Integer.parseInt(txt_banyak.getText().toString());
            data[3] = banyak;
            data[4] = harga * banyak;
            record.add(data);

            int index = 0;
            Object tampung[][] = new Object[record.size()][5];
            for (Object ambil[] : record) {
                tampung[index] = ambil;
                index = index + 1;
            }

            String header[] = {"ID Barang", "Nama Barang", "Harga", "Banyak", "Total Harga"};
            DefaultTableModel model = new DefaultTableModel(tampung, header);
            table_transaksi.setModel(model);

            txt_nama.setText("");
            txt_banyak.setText("");
            load_barang(txt_nama.getText().toString());
            setTotalHarga();
        }
    }//GEN-LAST:event_txt_banyakKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dialog = new JDialog();
        panelBayarCicilan p = new panelBayarCicilan(dialog);
        dialog.add(p);
        dialog.setTitle("");
        dialog.setSize(new Dimension(371, 321));
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        if (rbTidak.isSelected()) {
            if (table_transaksi.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "Maaf Anda Harus Transaksi Dahulu");
            } else {
                Insert_dataMember insert_member = new Insert_dataMember(this, true);
                insert_member.setVisible(true);
            }
        } else {
            if (table_transaksi.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "Maaf Anda Harus Transaksi Dahulu");
            } else {
                try {
                    String sql = "SELECT Id_Pelanggan FROM pelanggan WHERE Id_Pelanggan = '" + txtIdMember.getText() + "'";
                    PreparedStatement stm = connection.prepareStatement(sql);
                    ResultSet rs = stm.executeQuery();
                    if (rs.next()) {
                        dialog = new JDialog();
                        panelPelanggan p = new panelPelanggan(dialog, txtIdMember.getText(), totalHarga, "Trans" + (gsTrans.getTampung_id_trans() + 1), this);
                        dialog.add(p);
                        dialog.setTitle("");
                        dialog.setSize(new Dimension(471, 265));
                        dialog.setLocationRelativeTo(null);
                        dialog.setModal(true);
                        dialog.setResizable(false);
                        dialog.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "Id Member tidak ditemukan !");
                    }
                } catch (SQLException ex) {
                }
            }
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void rbYaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbYaActionPerformed
        txtIdMember.setEnabled(true);
        btnSubmit.setEnabled(true);
    }//GEN-LAST:event_rbYaActionPerformed

    private void rbTidakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTidakActionPerformed
        txtIdMember.setText("");
        txtIdMember.setEnabled(false);
    }//GEN-LAST:event_rbTidakActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnSubmit;
    public javax.swing.JButton btn_proses;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbox_toko;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotalHarga;
    private javax.swing.JLabel lblTotalHarga1;
    private javax.swing.JRadioButton rbTidak;
    private javax.swing.JRadioButton rbYa;
    private javax.swing.JTable table_barang;
    private javax.swing.JTable table_transaksi;
    private javax.swing.JTextField text_bayar;
    private javax.swing.JTextField txtIdMember;
    private javax.swing.JTextField txt_banyak;
    private javax.swing.JTextField txt_nama;
    // End of variables declaration//GEN-END:variables
}
