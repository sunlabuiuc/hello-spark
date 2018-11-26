import java.io.File
import java.net.URL
import java.nio.charset.Charset

import org.apache.commons.io.IOUtils
import org.apache.spark.rdd.RDD
import org.sunlab.utils.{ Awakable, FileFetchHelper }
import org.sunlab.utils.SparkHelper.{ sc, spark }

case class Bank(age: Integer, job: String, marital: String, education: String, balance: Integer)

/**
 * @author yu
 */
object Launcher extends App with Awakable {
  val timeStart = System.currentTimeMillis()

  lazy val kCWD = new File(".").getAbsolutePath.dropRight(1)
  lazy val dataPath = s"${kCWD}data/bank/bank.csv"

  FileFetchHelper.checkAndFetch(dataPath, "https://s3.amazonaws.com/apache-zeppelin/tutorial/bank/bank.csv")

  import spark.implicits._

  // load bank data
  //  val bank = spark.read.format("org.apache.spark.sql.execution.datasources.csv.CSVFileFormat").
  val bank = spark.read.format("com.databricks.spark.csv").
    option("header", "true").
    option("mode", "DROPMALFORMED").
    option("delimiter", ";").
    load(s"file://$dataPath")

  bank.createOrReplaceTempView("bank")

  spark.sql("select age, job from bank where age < 35 order by age desc limit 10").show()

  bank.filter('age < 35).sort('age.desc_nulls_last).select('age, 'job).limit(10).show()

  logger.info(s"finished , all time cost : ${System.currentTimeMillis() - timeStart}")

}
