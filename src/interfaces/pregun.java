/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import controladores.JugadorJpaController;
import static java.lang.String.valueOf;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import modelo.Jugador;
import modelo.Opcion;
import modelo.Pregunta;
import modelo.Ronda;

/**
 *
 * @author Tamagochita
 */
public class pregun extends javax.swing.JFrame {

    JugadorJpaController controller = new JugadorJpaController();
    EntityManager em = controller.getEntityManager();
    int ronda = 1;
    int acum = 0;
    String respuestaCorrecta = "";

    /**
     * Creates new form pregun
     */
    public pregun() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public pregun(String nombreJugador) {
        initComponents();
        //ronda = 1;
        lblNroRonda.setText(valueOf(ronda));
        lblAcum.setText(valueOf(acum));
        lblNom.setText(nombreJugador);
        mostrarPregunta();
    }

    void mostrarPregunta() {

        Query RsPregunta = em.createNativeQuery("SELECT * FROM pregunta WHERE idCategoria =? ORDER BY rand() LIMIT 1", Pregunta.class).setParameter(1, ronda);
        List<Pregunta> listaPreguntas = (List<Pregunta>) RsPregunta.getResultList();
        Pregunta pr = new Pregunta();
        pr = listaPreguntas.get(0);
        lblPregunta.setText(pr.getContenidoPregunta());
        Opcion opcion = new Opcion();
        Query RsOpciones = em.createNativeQuery("SELECT * FROM opcion WHERE idPregunta =?", Opcion.class).setParameter(1, pr.getIdPregunta());
        List<Opcion> listaOpciones = (List<Opcion>) RsOpciones.getResultList();
        if (!listaOpciones.isEmpty()) {
            for (int i = 0; i < listaOpciones.size(); i++) {
                opcion = listaOpciones.get(i);
                if (i == 0) {
                    rbtn1.setText(opcion.getContenidoOpcion());
                } else if (i == 1) {
                    rdbtn2.setText(opcion.getContenidoOpcion());
                } else if (i == 2) {
                    rdbtn3.setText(opcion.getContenidoOpcion());
                } else if (i == 3) {
                    rdbtn4.setText(opcion.getContenidoOpcion());
                }
                if (opcion.getEstadoRespuesta() == 1) {
                    respuestaCorrecta = opcion.getContenidoOpcion();
                }
            }
        }

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
        lblPregunta = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNom = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblAcum = new javax.swing.JLabel();
        lblNroRonda = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rbtn1 = new javax.swing.JRadioButton();
        rdbtn2 = new javax.swing.JRadioButton();
        rdbtn3 = new javax.swing.JRadioButton();
        rdbtn4 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        btnRetirarse = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setForeground(new java.awt.Color(204, 204, 255));

        lblPregunta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPregunta.setText("Enunciado");

        jLabel2.setText("Ronda");

        lblNom.setText("Nombre Concursante");

        jLabel8.setText("Acumulado");

        lblAcum.setText("0");

        lblNroRonda.setText("1");

        buttonGroup1.add(rbtn1);
        rbtn1.setText("jRadioButton1");

        buttonGroup1.add(rdbtn2);
        rdbtn2.setText("jRadioButton2");

        buttonGroup1.add(rdbtn3);
        rdbtn3.setText("jRadioButton3");

        buttonGroup1.add(rdbtn4);
        rdbtn4.setText("jRadioButton4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rdbtn3)
                    .addComponent(rbtn1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdbtn2)
                    .addComponent(rdbtn4))
                .addGap(110, 110, 110))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(rdbtn2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbtn1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbtn3)
                    .addComponent(rdbtn4))
                .addGap(25, 25, 25))
        );

        jButton1.setText("Siguiente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnRetirarse.setText("Retirarse");
        btnRetirarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirarseActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-staring-contest-50.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(btnRetirarse))
                        .addContainerGap(14, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblNroRonda)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(lblAcum))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNom)
                        .addGap(67, 67, 67))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPregunta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(lblNroRonda))
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lblAcum)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(lblNom)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(lblPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(40, 40, 40)
                        .addComponent(btnRetirarse)
                        .addGap(75, 75, 75))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (ronda <= 5) {
            try {
                if (rbtn1.isSelected()) {
                    if (rbtn1.getText().equals(respuestaCorrecta)) {
                        ronda++;
                        acum += 1000;
                        lblNroRonda.setText(valueOf(ronda));
                        lblAcum.setText(valueOf(acum));
                        mostrarPregunta();

                    } else {

                        JOptionPane.showMessageDialog(null, "Lo siento, perdiste");
                        Jugador jugador = new Jugador();
                        Ronda rond = new Ronda();
                        rond.setIdRonda(ronda);
                        jugador.setEstadoJugador(2);
                        jugador.setNombreJugador(lblNom.getText());
                        jugador.setIdRonda(rond);
                        jugador.setAcumuladoPremio(acum);
                        controller.create(jugador);
                        historico h = new historico();
                        h.setVisible(true);
                        this.dispose();
                    }

                }
                if (rdbtn2.isSelected()) {
                    if (rdbtn2.getText().equals(respuestaCorrecta)) {
                        ronda++;
                        acum += 1000;
                        lblNroRonda.setText(valueOf(ronda));
                        lblAcum.setText(valueOf(acum));
                        mostrarPregunta();

                    } else {
                        JOptionPane.showMessageDialog(null, "Lo siento, perdiste");
                        Jugador jugador = new Jugador();
                        Ronda rond = new Ronda();
                        rond.setIdRonda(ronda);
                        jugador.setEstadoJugador(2);
                        jugador.setNombreJugador(lblNom.getText());
                        jugador.setIdRonda(rond);
                        jugador.setAcumuladoPremio(acum);
                        controller.create(jugador);
                        historico h = new historico();
                        h.setVisible(true);
                        this.dispose();
                    }
                }
                if (rdbtn3.isSelected()) {
                    if (rdbtn3.getText().equals(respuestaCorrecta)) {
                        if (ronda <= 5) {
                            ronda++;
                            acum += 1000;
                            lblNroRonda.setText(valueOf(ronda));
                            lblAcum.setText(valueOf(acum));
                            mostrarPregunta();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Lo siento, perdiste");
                        Jugador jugador = new Jugador();
                        Ronda rond = new Ronda();
                        rond.setIdRonda(ronda);
                        jugador.setEstadoJugador(2);
                        jugador.setNombreJugador(lblNom.getText());
                        jugador.setIdRonda(rond);
                        jugador.setAcumuladoPremio(acum);
                        controller.create(jugador);
                        historico h = new historico();
                        h.setVisible(true);
                        this.dispose();
                    }

                }
                if (rdbtn4.isSelected()) {
                    if (rdbtn4.getText().equals(respuestaCorrecta)) {
                        if (ronda <= 5) {
                            ronda++;
                            acum += 1000;
                            lblNroRonda.setText(valueOf(ronda));
                            lblAcum.setText(valueOf(acum));
                            mostrarPregunta();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Lo siento, perdiste");
                        Jugador jugador = new Jugador();
                        Ronda rond = new Ronda();
                        rond.setIdRonda(ronda);
                        jugador.setEstadoJugador(2);
                        jugador.setNombreJugador(lblNom.getText());
                        jugador.setIdRonda(rond);
                        jugador.setAcumuladoPremio(acum);
                        controller.create(jugador);
                        historico h = new historico();
                        h.setVisible(true);
                        this.dispose();
                    }

                }

            } catch (Exception e) {
            }
        } else {
            lblNroRonda.setText("5");
            JOptionPane.showMessageDialog(null, "Felicitaciones!");
            Jugador jugador = new Jugador();
            Ronda rond = new Ronda();
            rond.setIdRonda(5);
            jugador.setEstadoJugador(1);
            jugador.setNombreJugador(lblNom.getText());
            jugador.setIdRonda(rond);
            jugador.setAcumuladoPremio(acum);
            controller.create(jugador);
            historico h = new historico();
            h.setVisible(true);
            this.dispose();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnRetirarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarseActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Gracias por jugar");
        Jugador jugador = new Jugador();
        Ronda rond = new Ronda();
        rond.setIdRonda(ronda);
        jugador.setEstadoJugador(0);
        jugador.setNombreJugador(lblNom.getText());
        jugador.setIdRonda(rond);
        jugador.setAcumuladoPremio(acum);
        controller.create(jugador);
        historico h = new historico();
        h.setVisible(true);
        this.dispose();


    }//GEN-LAST:event_btnRetirarseActionPerformed

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
            java.util.logging.Logger.getLogger(pregun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pregun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pregun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pregun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pregun().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRetirarse;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAcum;
    private javax.swing.JLabel lblNom;
    private javax.swing.JLabel lblNroRonda;
    private javax.swing.JLabel lblPregunta;
    private javax.swing.JRadioButton rbtn1;
    private javax.swing.JRadioButton rdbtn2;
    private javax.swing.JRadioButton rdbtn3;
    private javax.swing.JRadioButton rdbtn4;
    // End of variables declaration//GEN-END:variables
}
