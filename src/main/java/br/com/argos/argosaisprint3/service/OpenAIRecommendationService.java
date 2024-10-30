package br.com.argos.argosaisprint3.service;

import br.com.argos.argosaisprint3.model.Recommendation;
import br.com.argos.argosaisprint3.model.Produto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenAIRecommendationService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final ProdutoService produtoService;

    @Autowired
    public OpenAIRecommendationService(ProdutoService produtoService) {

        this.produtoService = produtoService;
    }

    public List<Recommendation> getProductRecommendations(int age, String sexo) throws IOException {
        OkHttpClient client = new OkHttpClient();

        List<Produto> produtos = produtoService.findAll();
        List<String> productDescriptions = produtos.stream()
                .map(produto -> produto.getNome() + " - " + produto.getDescricao() + " (R$" + produto.getPreco() + ")"
                        + " [Imagem: " + produto.getImagem() + "]")
                .collect(Collectors.toList());

        String prompt = "Sugira produtos para uma pessoa de sexo " + sexo + " e com " + age + " anos de idade, considerando os seguintes produtos:\n"
                + String.join(", ", productDescriptions);

        JSONObject json = new JSONObject();
        json.put("model", "gpt-3.5-turbo");
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "user").put("content", prompt));
        json.put("messages", messages);

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JSONObject responseBody = new JSONObject(response.body().string());
                String aiResponse = responseBody.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content").trim();

                List<Recommendation> recommendations = new ArrayList<>();
                for (Produto produto : produtos) {
                    if (aiResponse.contains(produto.getNome())) {
                        recommendations.add(new Recommendation(
                                produto.getNome(),
                                produto.getDescricao(),
                                produto.getPreco(),
                                produto.getImagem()
                        ));
                    }
                }
                return recommendations;
            } else {
                throw new IOException("Erro na requisição: " + response);
            }
        }
    }
}
