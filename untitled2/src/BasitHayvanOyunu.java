import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Hayvan {
    String isim;
    int toklukSeviyesi;
    int miktar; // İnek için süt, koyun için yün, tavuk için yumurta

    public Hayvan(String isim) {
        this.isim = isim;
        this.toklukSeviyesi = new Random().nextInt(6) + 5; // Tokluk seviyesini 5-10 arasında başlat
        this.miktar = 0;
    }

    public String getOzellik() {
        switch (isim) {
            case "İnek":
                return "Süt Miktarı: " + miktar;
            case "Koyun":
                return "Yün Miktarı: " + miktar;
            case "Tavuk":
                return "Yumurta Miktarı: " + miktar;
            default:
                return "";
        }
    }
}

public class BasitHayvanOyunu extends JFrame {
    private JTextArea hayvanEkrani;
    private JLabel sutLabel;
    private JLabel yunLabel;
    private JLabel yumurtaLabel;
    private JLabel besinMiktariLabel;
    private int toplamSut = 0;
    private int toplamYun = 0;
    private int toplamYumurta = 0;
    private int besinMiktari = 20;
    private List<Hayvan> hayvanlar = new ArrayList<>();
    private String[] hayvanTurleri = {"İnek", "Tavuk", "Koyun"};

    public BasitHayvanOyunu() {
        setTitle("Basit Hayvan Oyunu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Hayvan Ekranı
        hayvanEkrani = new JTextArea();
        hayvanEkrani.setEditable(false);
        add(new JScrollPane(hayvanEkrani), BorderLayout.CENTER);

        // Alt Panel: Butonlar ve Durumlar
        JPanel altPanel = new JPanel();
        JButton hayvanYaratButton = new JButton("Hayvan Yarat");
        JButton yeniGunButton = new JButton("Yeni Gün");
        JButton besleButton = new JButton("Besle Tüm Hayvanları");

        altPanel.add(hayvanYaratButton);
        altPanel.add(yeniGunButton);
        altPanel.add(besleButton);
        add(altPanel, BorderLayout.SOUTH);

        // Toplanan Miktarlar
        sutLabel = new JLabel("Toplanan Süt: 0");
        yunLabel = new JLabel("Toplanan Yün: 0");
        yumurtaLabel = new JLabel("Toplanan Yumurta: 0");
        besinMiktariLabel = new JLabel("Besin Miktarı: 20");

        JPanel yukariPanel = new JPanel(new GridLayout(4, 1));
        yukariPanel.add(sutLabel);
        yukariPanel.add(yunLabel);
        yukariPanel.add(yumurtaLabel);
        yukariPanel.add(besinMiktariLabel);
        add(yukariPanel, BorderLayout.NORTH);

        // Hayvan Yaratma Butonu
        hayvanYaratButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                String secilenHayvanTur = hayvanTurleri[random.nextInt(hayvanTurleri.length)];
                Hayvan yeniHayvan = new Hayvan(secilenHayvanTur);
                hayvanlar.add(yeniHayvan);
                guncelleEkran();
            }
        });

        // Yeni Gün Butonu
        yeniGunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Hayvan hayvan : hayvanlar) {
                    if (hayvan.toklukSeviyesi > 0) {
                        hayvan.toklukSeviyesi--;
                        // Sadece tokluk seviyesi 0'dan büyükse miktar artırılır
                        if (hayvan.toklukSeviyesi > 0) {
                            hayvan.miktar++;
                            switch (hayvan.isim) {
                                case "İnek":
                                    toplamSut++;
                                    break;
                                case "Koyun":
                                    toplamYun++;
                                    break;
                                case "Tavuk":
                                    toplamYumurta++;
                                    break;
                            }
                        }
                    }
                }
                besinMiktari = 20; // Yeni günde besin miktarını 20 yap
                sutLabel.setText("Toplanan Süt: " + toplamSut);
                yunLabel.setText("Toplanan Yün: " + toplamYun);
                yumurtaLabel.setText("Toplanan Yumurta: " + toplamYumurta);
                besinMiktariLabel.setText("Besin Miktarı: " + besinMiktari);
                guncelleEkran();
            }
        });

        // Besle Tüm Hayvanları Butonu
        besleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (besinMiktari > 0) {
                    for (Hayvan hayvan : hayvanlar) {
                        if (hayvan.toklukSeviyesi < 10) {
                            hayvan.toklukSeviyesi++;
                            besinMiktari--;
                        }
                    }
                    besinMiktariLabel.setText("Besin Miktarı: " + besinMiktari);
                    guncelleEkran();
                }
            }
        });

        setVisible(true);
    }

    private void guncelleEkran() {
        StringBuilder ekranMetni = new StringBuilder();
        for (Hayvan hayvan : hayvanlar) {
            ekranMetni.append("Hayvan: ").append(hayvan.isim)
                    .append(" | Tokluk: ").append(hayvan.toklukSeviyesi)
                    .append(" | ").append(hayvan.getOzellik()).append("\n");
        }
        hayvanEkrani.setText(ekranMetni.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BasitHayvanOyunu());
    }
}
