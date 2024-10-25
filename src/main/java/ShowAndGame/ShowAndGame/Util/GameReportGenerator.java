package ShowAndGame.ShowAndGame.util;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameReportGenerator {

    public byte[] exportToPdf(List<Game> games) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(games));
    }

    private JasperPrint getReport(List<Game> games) throws FileNotFoundException, JRException {
        if (games == null || games.isEmpty()) {
            System.out.println("La lista de juegos está vacía o es nula.");
        } else {
            System.out.println("Lista de juegos contiene los siguientes datos:");
            for (Game game : games) {
                System.out.println("ID: " + game.getId() + ", Name: " + game.getName() +
                        ", Followers: " + game.getFollowerAmount() + ", Rating: " + game.getRating());
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("gamesData", new JRBeanCollectionDataSource(games));

        JasperPrint report = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(
                        ResourceUtils.getFile("classpath:gamesReport.jrxml").getAbsolutePath()),
                params,
                new JREmptyDataSource()
        );

        return report;
    }
}
