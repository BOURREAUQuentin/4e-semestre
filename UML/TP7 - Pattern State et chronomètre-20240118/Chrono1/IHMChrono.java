import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class IHMChrono extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	JButton bgo = new JButton("GO");
	JButton bstop = new JButton("STOP");
	Chrono chrono;

	public IHMChrono(int duree) {
		setPreferredSize(new Dimension(500,300));
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		chrono = new Chrono(this, duree, 200, 120, 100);		
		bgo.addActionListener(this);
		bstop.addActionListener(this);
		add(bgo);
		add(bstop);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();

		if (source == bgo) chrono.go();
        else if  (source == bstop) chrono.stop();
	}

	public void paintComponent(Graphics g)  {
		super.paintComponent(g);
		chrono.dessine(g);
	}
}
