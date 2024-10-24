package ShowAndGame.ShowAndGame.util;

import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostForReportDto;
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
public class PostReportGenerator {

    public byte[] exportToPdf(List<GetFeedPostForReportDto> posts) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(posts));
    }

    private JasperPrint getReport(List<GetFeedPostForReportDto> posts) throws FileNotFoundException, JRException {
        if (posts == null || posts.isEmpty()) {
            System.out.println("La lista de publicaciones está vacía o es nula.");
        } else {
            System.out.println("Lista de publicaciones contiene los siguientes datos:");
            for (GetFeedPostForReportDto post : posts) {
                System.out.println("ID: " + post.getId() + ", Username: " + post.getUsername() +
                        ", Likes: " + post.getLikesCounter() + ", Date: " + post.getDate());
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("postsData", new JRBeanCollectionDataSource(posts));

        JasperPrint report = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(
                        ResourceUtils.getFile("classpath:postsReport.jrxml").getAbsolutePath()),
                params,
                new JREmptyDataSource()
        );

        return report;
    }
}
