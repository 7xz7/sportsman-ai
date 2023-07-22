package com.mnemocon.sportsman.ai
// Импортирование необходимых библиотек и модулей
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mnemocon.sportsman.ai.databinding.FragmentInfoBinding
// Основной класс фрагмента, который отображает информацию
class InfoFragment : Fragment() {
    // Объявление переменных для привязки и текстовых строк
    private lateinit var binding: FragmentInfoBinding

    private val description: String = "<p> При создании этого приложения было использовано машинное обучение. Области машинного обучения, которые мы использовали, - это оценка позы и кластеризация K-средних. Оценка позы используется для определения ключевых точек человеческого тела и отслеживания их движений. Кластеризация K-means - это алгоритм машинного обучения без контроля. По сути, он берет набор данных, выполняет поиск шаблона внутри него и группирует похожие данные вместе в кластер. Следовательно, образуется множество кластеров. <br/> Здесь, в нашем приложении, мы обучили неконтролируемую модель кластера K-means, обучив ее на наборе данных, содержащем сотни изображений отжиманий и приседаний. Эти изображения были сделаны под разными углами, чтобы модель могла найти обобщенное решение и избежать переобучения.<p>"
    private val title: String = "Магия машинного обучения"
    // Функция для создания представления фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        binding = FragmentInfoBinding.inflate(layoutInflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner

            var isToolbarShown = false
            // Обработчик прокрутки для показа или скрытия панели инструментов
            // прослушиватель изменения прокрутки начинается с Y = 0, когда изображение полностью свернуто
            aicameraDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // Пользователь прокрутил изображение до высоты панели инструментов, и текст заголовка находится
                    // под панелью инструментов, поэтому панель инструментов должна быть показана.
                    // Проверка, нужно ли показывать панель инструментов
                    val shouldShowToolbar = scrollY > toolbar.height

                    // Новое состояние панели инструментов отличается от предыдущего состояния; обновить
                    // атрибуты панели приложений и панели инструментов.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Используйте shadow animator для добавления высоты, если отображается панель инструментов
                        appbar.isActivated = shouldShowToolbar

                        // Показать название растения, если отображается панель инструментов
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            setHasOptionsMenu(true)
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()// Возвращение на предыдущий экран
            }
            // Установка текста описания и заголовка
            aicameraDescription.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(description)
            }

            aicameraDetailName.text = title

        }



        return binding.root// Возвращение корневого представления
    }
    // Функция, вызываемая при отсоединении фрагмента
    override fun onDetach() {
        super.onDetach()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}