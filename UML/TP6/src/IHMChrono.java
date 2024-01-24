import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class IHMChrono extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	JButton bgostop = new JButton("GO/STOP");
	JButton bclear = new JButton("CLEAR");
	Chrono chrono;

	public IHMChrono(int duree) {
		setPreferredSize(new Dimension(500,300));
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		IEtat etatInitial = new Arret();
        Automate controleur = new Automate(etatInitial);
		chrono = new Chrono(controleur, this, duree, 200, 120, 100);
		controleur.setControle(chrono);
		bgostop.addActionListener(this);
		bclear.addActionListener(this);
		add(bgostop);
		add(bclear);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == bgostop) chrono.goStop();
        else if  (source == bclear) chrono.clear();
	}

	public void paintComponent(Graphics g)  {
		super.paintComponent(g);
		chrono.dessine(g);
	}
}
