package  com.tagava.data

import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


data class ArticleItem(
    val comments: Int,
    val content: String,
    val createdAt: String,
    val id: String,
    val likes: Int
) {
    companion object {

        @JvmStatic
        @BindingAdapter("formatPostDate")
        fun bindFormatPostDate(view: TextView, dateString: String) {

            val dateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

            var convertedDate: Date? = Date()

            try {
                convertedDate = dateFormat.parse(dateString)
            } catch (e: ParseException) {

                e.printStackTrace()
            }

            val p = PrettyTime()

            val datetime: String = p.format(convertedDate)
            view.text = datetime
        }

        @JvmStatic
        @BindingAdapter("formatComments")
        fun bindFormatComments(view: TextView, number: Int) {
            view.text = shortNumber(number) + " Comments"
        }

        @JvmStatic
        @BindingAdapter("formatLikes")
        fun bindFormatLikes(view: TextView, number: Int) {
            view.text = shortNumber(number) + " Likes"
        }

        fun shortNumber(number: Int): String {
            var numberString = ""
            numberString = if (Math.abs(number / 1000000) > 1) {
                (number / 1000000).toString().toString() + "m"
            } else if (Math.abs(number / 1000) > 1) {
                (number / 1000).toString().toString() + "k"
            } else {
                number.toString()
            }
            return numberString
        }

        val CALLBACK: DiffUtil.ItemCallback<ArticleItem> =
            object : DiffUtil.ItemCallback<ArticleItem>() {
                override fun areItemsTheSame(
                    @NonNull article: ArticleItem,
                    @NonNull t1: ArticleItem
                ): Boolean {
                    return article.id === t1.id
                }

                override fun areContentsTheSame(
                    @NonNull article: ArticleItem,
                    @NonNull t1: ArticleItem
                ): Boolean {
                    return true
                }
            }
    }


}