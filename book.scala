import scala.collection.immutable.ListMap
import java.io._ 

object book {
    def main(args: Array[String]): Unit = {
        val bookfile = scala.io.Source.fromFile("crime.txt")
		var text = try bookfile.mkString.toLowerCase() finally bookfile.close()
        text = text.replaceAll("\\p{Punct}", "")
		var words=text.split("\\s+") //split on whitespaces
		val stopwordsfile = scala.io.Source.fromFile("stop_words_english.txt")
		val stopwords = try stopwordsfile.getLines.toArray finally stopwordsfile.close()
        words = words.filter(!stopwords.contains(_))
		//println(words.slice(1,100).mkString(" "))
		var wordcount = words.groupBy(identity).mapValues(_.size) //map with counting
		wordcount = ListMap(wordcount.toSeq.sortWith(_._2 > _._2):_*)
		wordcount.take(100) foreach (x => println (x._1 + "," + x._2))
    	val file = new File("crime.csv")
    	val bw = new BufferedWriter(new FileWriter(file))
		wordcount.take(100) foreach (x => bw.write(x._1 + "," + x._2 + "\n"))
    	bw.close()
}
}
