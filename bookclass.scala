import scala.collection.immutable.ListMap
import java.io._ 
import scala.io.StdIn._

class WordCount {
		var map = Map[String, Int]()
		private var words = Array[String]()
		private val stopwordsfile = scala.io.Source.fromFile("stop_words_english.txt")
		private val stopwords = try stopwordsfile.getLines.toArray finally stopwordsfile.close()

		def addString(text: String): Unit = {
        	var texttoadd = text.toLowerCase().replaceAll("\\p{Punct}", "")
        	words = words ++ texttoadd.split("\\s+").filter(!stopwords.contains(_))
			map = words.groupBy(identity).mapValues(_.size) 
    	}
		def addFromFile(filename: String): Unit = {
        	val bookfile = scala.io.Source.fromFile(filename)
			var text = try bookfile.mkString finally bookfile.close()
			addString(text)
    	}
		def print(n: Int): Unit = {
        	map = ListMap(map.toSeq.sortWith(_._2 > _._2):_*)
			map.take(n) foreach (x => println (x._1 + "," + x._2))
    	}
		def writetofile(n: Int, filename: String): Unit = {
			val file = new File(filename)
			val bw = new BufferedWriter(new FileWriter(file))
        	map = ListMap(map.toSeq.sortWith(_._2 > _._2):_*)
			map.take(n) foreach (x => bw.write(x._2 + "," + x._1 + "\n"))
    		bw.close()
    	}


}

object bookclass {
    def main(args: Array[String]): Unit = {
        var words = new WordCount
		var text: String = null
		var filename: String = null
		var n: Int = 0
		println("Welcome to Word Cloud Generator!\nIf you want to:\n-add string: type 1\n-add text from file: type 2\n-print the most common words: type 3\n-write the most common words to file: type 4\n-exit: type 0")
		var a = readInt()
		while( a != 0 ){
               if( a == 1 ){
         			println("Type the text you want to add (keep in mind that typing enter will stop the input)")
					text = readLine()
					words.addString(text)
      			} else if( a == 2 ){
         			println("Type name of the file you want to add")
					filename = readLine()
					words.addFromFile(filename)
      			} else if( a == 3 ){
         			println("Type the number of most common words you want to print")
					n = readInt()
					words.print(n)
      			} else if(a == 4){
        			 println("Type the number of most common words you want to write to file")
					 n = readInt()
					 println("Type name of the file")
					 filename = readLine()
					words.writetofile(n, filename)
      			} else {println("Please type a correct number")}
         		println("What do you want to do next?")
				a = readInt()
      }  
}
}

