package com.dbiServer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.FloatNdArray;
import org.tensorflow.ndarray.StdArrays;
import org.tensorflow.proto.MetaGraphDef;
import org.tensorflow.proto.SignatureDef;
import org.tensorflow.types.TFloat32;

import com.dbiServer.DTOs.PredictionResult;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;

@Service
public class PredictionService {
  // test comment for deployment

  @Autowired
  private SavedModelBundle model;

  public static final String[] BREED_LABELS = {
      "n02085620-Chihuahua", "n02085782-Japanese_spaniel",
      "n02085936-Maltese_dog", "n02086079-Pekinese", "n02086240-Shih-Tzu",
      "n02086646-Blenheim_spaniel", "n02086910-papillon", "n02087046-toy_terrier",
      "n02087394-Rhodesian_ridgeback", "n02088094-Afghan_hound",
      "n02088238-basset", "n02088364-beagle", "n02088466-bloodhound",
      "n02088632-bluetick", "n02089078-black-and-tan_coonhound",
      "n02089867-Walker_hound", "n02089973-English_foxhound", "n02090379-redbone",
      "n02090622-borzoi", "n02090721-Irish_wolfhound",
      "n02091032-Italian_greyhound", "n02091134-whippet",
      "n02091244-Ibizan_hound", "n02091467-Norwegian_elkhound",
      "n02091635-otterhound", "n02091831-Saluki", "n02092002-Scottish_deerhound",
      "n02092339-Weimaraner", "n02093256-Staffordshire_bullterrier",
      "n02093428-American_Staffordshire_terrier", "n02093647-Bedlington_terrier",
      "n02093754-Border_terrier", "n02093859-Kerry_blue_terrier",
      "n02093991-Irish_terrier", "n02094114-Norfolk_terrier",
      "n02094258-Norwich_terrier", "n02094433-Yorkshire_terrier",
      "n02095314-wire-haired_fox_terrier", "n02095570-Lakeland_terrier",
      "n02095889-Sealyham_terrier", "n02096051-Airedale", "n02096177-cairn",
      "n02096294-Australian_terrier", "n02096437-Dandie_Dinmont",
      "n02096585-Boston_bull", "n02097047-miniature_schnauzer",
      "n02097130-giant_schnauzer", "n02097209-standard_schnauzer",
      "n02097298-Scotch_terrier", "n02097474-Tibetan_terrier",
      "n02097658-silky_terrier", "n02098105-soft-coated_wheaten_terrier",
      "n02098286-West_Highland_white_terrier", "n02098413-Lhasa",
      "n02099267-flat-coated_retriever", "n02099429-curly-coated_retriever",
      "n02099601-golden_retriever", "n02099712-Labrador_retriever",
      "n02099849-Chesapeake_Bay_retriever",
      "n02100236-German_short-haired_pointer", "n02100583-vizsla",
      "n02100735-English_setter", "n02100877-Irish_setter",
      "n02101006-Gordon_setter", "n02101388-Brittany_spaniel",
      "n02101556-clumber", "n02102040-English_springer",
      "n02102177-Welsh_springer_spaniel", "n02102318-cocker_spaniel",
      "n02102480-Sussex_spaniel", "n02102973-Irish_water_spaniel",
      "n02104029-kuvasz", "n02104365-schipperke", "n02105056-groenendael",
      "n02105162-malinois", "n02105251-briard", "n02105412-kelpie",
      "n02105505-komondor", "n02105641-Old_English_sheepdog",
      "n02105855-Shetland_sheepdog", "n02106030-collie",
      "n02106166-Border_collie", "n02106382-Bouvier_des_Flandres",
      "n02106550-Rottweiler", "n02106662-German_shepherd", "n02107142-Doberman",
      "n02107312-miniature_pinscher", "n02107574-Greater_Swiss_Mountain_dog",
      "n02107683-Bernese_mountain_dog", "n02107908-Appenzeller",
      "n02108000-EntleBucher", "n02108089-boxer", "n02108422-bull_mastiff",
      "n02108551-Tibetan_mastiff", "n02108915-French_bulldog",
      "n02109047-Great_Dane", "n02109525-Saint_Bernard", "n02109961-Eskimo_dog",
      "n02110063-malamute", "n02110185-Siberian_husky", "n02110627-affenpinscher",
      "n02110806-basenji", "n02110958-pug", "n02111129-Leonberg",
      "n02111277-Newfoundland", "n02111500-Great_Pyrenees", "n02111889-Samoyed",
      "n02112018-Pomeranian", "n02112137-chow", "n02112350-keeshond",
      "n02112706-Brabancon_griffon", "n02113023-Pembroke", "n02113186-Cardigan",
      "n02113624-toy_poodle", "n02113712-miniature_poodle",
      "n02113799-standard_poodle", "n02113978-Mexican_hairless",
      "n02115641-dingo", "n02115913-dhole", "n02116738-African_hunting_dog"
  };

  // private static final String INPUT_TENSOR =
  // "serving_default_keras_tensor_311:0"; // for v1.1 tag
  private static final String INPUT_TENSOR = "serving_default_keras_tensor_313:0"; // for Model tag
  private static final String OUTPUT_TENSOR = "StatefulPartitionedCall_1:0";

  public PredictionResult predictFromUrl(String url) throws IOException {
    // Read image from URL
    BufferedImage img = ImageIO.read(URI.create(url).toURL());
    // Preprocess image to 224x224 and normalize
    TFloat32 inputTensor = processImageToTensor(img);
    // Predict
    return predict(inputTensor);
  }

  public PredictionResult predictFromFile(MultipartFile file) throws IOException {
    // Read image from uploaded file
    BufferedImage img = ImageIO.read(file.getInputStream());
    // Preprocess image to 224x224 and normalize
    TFloat32 inputTensor = processImageToTensor(img);
    // Predict
    return predict(inputTensor);
  }

  private TFloat32 processImageToTensor(BufferedImage img) {
    // Resize to 224x224
    BufferedImage resizedImg = new BufferedImage(224, 224, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphic = resizedImg.createGraphics();
    graphic.drawImage(img, 0, 0, 224, 224, null);
    graphic.dispose();
    // Alternatively
    // resizedImg.getGraphics().drawImage(img, 0, 0, 224, 224, null);

    // Convert to float array and normalize
    float[][][][] inputData = new float[1][224][224][3];
    for (int y = 0; y < 224; y++) {
      for (int x = 0; x < 224; x++) {
        int rgb = resizedImg.getRGB(x, y); // get pixel as int
        int r = (rgb >> 16) & 0xFF; // extract red
        int g = (rgb >> 8) & 0xFF; // extract green
        int b = (rgb) & 0xFF; // extract blue
        // normalize to [0,1] range
        inputData[0][y][x][0] = (r / 127.5f) - 1.0f;
        inputData[0][y][x][1] = (g / 127.5f) - 1.0f;
        inputData[0][y][x][2] = (b / 127.5f) - 1.0f;
      }
    }
    return TFloat32.tensorOf(StdArrays.ndCopyOf(inputData));
  }

  private PredictionResult predict(TFloat32 inputTensor) {
    // Run the model
    Tensor output = model.session().runner()
        .feed(INPUT_TENSOR, inputTensor)
        .fetch(OUTPUT_TENSOR)
        .run()
        .get(0);

    // Process output tensor to get probabilities
    FloatNdArray outputTensor = (TFloat32) output;
    float[][] probabilities = new float[1][BREED_LABELS.length];
    for (int i = 0; i < BREED_LABELS.length; i++) {
      probabilities[0][i] = outputTensor.getFloat(0, i);
    }

    // Find result with highest probability
    int bestIdx = 0;
    float bestVal = probabilities[0][0];
    for (int i = 1; i < BREED_LABELS.length; i++) {
      if (probabilities[0][i] > bestVal) {
        bestVal = probabilities[0][i];
        bestIdx = i;
      }
    }

    return new PredictionResult(BREED_LABELS[bestIdx], bestVal);
  }

  public byte[] getProcessedImage(String url) throws IOException {
    BufferedImage img = ImageIO.read(URI.create(url).toURL());
    BufferedImage resizedImg = new BufferedImage(224, 224, BufferedImage.TYPE_INT_RGB);
    resizedImg.getGraphics().drawImage(img, 0, 0, 224, 224, null);
    // Convert to byte array
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(resizedImg, "png", baos);
    byte[] imageBytes = baos.toByteArray();
    return imageBytes;
  }

  public String checkModel() {
    StringBuilder info = new StringBuilder();
    MetaGraphDef metaGraphs = model.metaGraphDef();
    for (String sigName : metaGraphs.getSignatureDefMap().keySet()) {
      SignatureDef signature = metaGraphs.getSignatureDefMap().get(sigName);
      info.append("Signature: ").append(sigName).append("\n");
      info.append("Signature Details: ").append(signature.toString()).append("\n");
      info.append("Inputs: ").append(signature.getInputsMap().toString()).append("\n");
      info.append("Outputs: ").append(signature.getOutputsMap().toString()).append("\n");
    }
    return info.toString();
  }
}
