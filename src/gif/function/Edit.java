package gif.function;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifFrame;
import com.gif4j.GifImage;

public class Edit {

	public boolean edit(File file ){
		try {
			long size = file.length();
			GifImage image = GifDecoder.decode(file);
			int imageFrameNumber = image.getNumberOfFrames();
			GifFrame[] frames = new GifFrame[imageFrameNumber];
			
			for(int i = 0; i < imageFrameNumber; i++){
				frames[i] = image.getFrame(i);
			}
			
			String outPath = file.getParentFile().getPath() + "\\simple";
			String outName = file.getName();
			
			write(frames, size, outPath , outName);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void write(GifFrame[] images, long size, String outputDir, String outImageName){
     
        try {
            int imagePageCount = images.length;
            int delay = images[0].getDelay();
            List<GifFrame> imgs = new ArrayList<GifFrame>();
            
            double frameSize = size * 1.0 / imagePageCount;
            int count = (int)(1 * 1024 * 1024 / frameSize);
            int interval = (imagePageCount / count);
            
            System.out.println(imagePageCount + "÷°");
            System.out.println(frameSize/1024/1024 + " MB/÷°");
            System.out.println(count + "÷°/Mb");
            System.out.println("√øº‰∏Ù" + interval + "÷°≥È»°1÷°");
            System.out.println("");
            
            
            delay = (int) (delay * interval);
            
            for(int i = 0; i < imagePageCount; i++){
            	if(i == 0 || (interval < i && i % interval == 0)){
            		imgs.add(images[i]);
            	}
            }
           
            
            GifImage gifImage = new GifImage();
            gifImage.setLoopNumber(0);
            
            for (GifFrame f : imgs){
            	f.setDelay(delay);
                gifImage.addGifFrame(f);
            }
            
            File file = new File(outputDir);
            if(!file.exists()){
            	file.mkdirs();
            }
            
            GifEncoder.encode(gifImage, new File(outputDir, outImageName), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
