package com.palefat.nanomerge.video;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.builder.Mp4Builder;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jake on 10/2/16.
 */
public class VideoMerger {


    private void TestMerging(List<Movie> movies){

        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();


        for (Movie m : movies) {
            for (Track track : m.getTracks()) {
                if (track.getHandler().equals("vide")) {
                    videoTracks.add(track);
                }
                if (track.getHandler().equals("soun")) {
                    audioTracks.add(track);
                }
            }
        }

        Movie concatMovie = new Movie();


        try {
            concatMovie.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            concatMovie.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));


            Container out2 = new DefaultMp4Builder().build(concatMovie);

            WritableByteChannel outputChannel = new RandomAccessFile(String.format("output.mp4"), "rw").getChannel();

            out2.writeContainer(outputChannel);


            if(outputChannel.isOpen())
                outputChannel.close();

        }catch (Exception e) {

        }
    }
}
