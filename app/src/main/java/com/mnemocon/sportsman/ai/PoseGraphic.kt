package com.mnemocon.sportsman.ai

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.google.common.primitives.Ints
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import java.lang.Math.max
import java.lang.Math.min
import java.util.Locale

/**
 * Рисует обнаруженную позу на предварительном просмотре.
 */
class PoseGraphic internal constructor(
  overlay: GraphicOverlay,
  private val pose: Pose, // Обнаруженная поза
  private val showInFrameLikelihood: Boolean, // Флаг для показа вероятности позы в кадре
  private val visualizeZ: Boolean, // Флаг для визуализации оси Z
  private val rescaleZForVisualization: Boolean, // Флаг для масштабирования оси Z для визуализации
  private val poseClassification: List<String>, // Классификация позы
  private val ff: Boolean
) : GraphicOverlay.Graphic(overlay) {
  private var zMin = java.lang.Float.MAX_VALUE // Минимальное значение по оси Z
  private var zMax = java.lang.Float.MIN_VALUE // Максимальное значение по оси Z
  private val classificationTextPaint: Paint // Краска для текста классификации
  private val leftPaint: Paint // Краска для левой стороны
  private val rightPaint: Paint // Краска для правой стороны
  private val whitePaint: Paint // Белая краска

  init {
    // Инициализация параметров краски
    classificationTextPaint = Paint()
    classificationTextPaint.color = Color.WHITE
    classificationTextPaint.textSize = POSE_CLASSIFICATION_TEXT_SIZE
    classificationTextPaint.setShadowLayer(5.0f, 0f, 0f, Color.BLACK)

    whitePaint = Paint()
    whitePaint.strokeWidth = STROKE_WIDTH
    whitePaint.color = Color.WHITE
    whitePaint.textSize = IN_FRAME_LIKELIHOOD_TEXT_SIZE
    leftPaint = Paint()
    leftPaint.strokeWidth = STROKE_WIDTH
    leftPaint.color = Color.GREEN
    rightPaint = Paint()
    rightPaint.strokeWidth = STROKE_WIDTH
    rightPaint.color = Color.YELLOW
  }
  // Функция для рисования позы на холсте
  override fun draw(canvas: Canvas) {
    val landmarks = pose.allPoseLandmarks
    if (landmarks.isEmpty()) {
      return
    }

    // Рисование текста классификации позы
    val classificationX = POSE_CLASSIFICATION_TEXT_SIZE * 0.5f
    for (i in poseClassification.indices) {
      val classificationY = canvas.height - (
        POSE_CLASSIFICATION_TEXT_SIZE * 1.5f * (poseClassification.size - i).toFloat()
        )
      if(i == 0 && poseClassification.size == 2) continue
      if(!ff) continue
      canvas.drawText(
        poseClassification[i],
        classificationX,
        classificationY,
        classificationTextPaint
      )
    }

    // Построить все точки
    for (landmark in landmarks) {
      drawPoint(canvas, landmark, whitePaint)
      if (visualizeZ && rescaleZForVisualization) {
        zMin = min(zMin, landmark.position3D.z)
        zMax = max(zMax, landmark.position3D.z)
      }
    }
// Извлечение ключевых точек (landmarks) лица из обнаруженной позы.

    val nose = pose.getPoseLandmark(PoseLandmark.NOSE)
    val lefyEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
    val lefyEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
    val leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
    val rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
    val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)
    val rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
    val leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR)
    val rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR)
    val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
    val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)

// Извлечение ключевых точек (landmarks) верхней и нижней части тела из обнаруженной позы.

    val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
    val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
    val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
    val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
    val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
    val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
    val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
    val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
    val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
    val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
    val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
    val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

// Извлечение ключевых точек (landmarks) рук и ног из обнаруженной позы.

    val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
    val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
    val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
    val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
    val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
    val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
    val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
    val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
    val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
    val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)

    // Лицо
    drawLine(canvas, nose, lefyEyeInner, whitePaint)
    drawLine(canvas, lefyEyeInner, lefyEye, whitePaint)
    drawLine(canvas, lefyEye, leftEyeOuter, whitePaint)
    drawLine(canvas, leftEyeOuter, leftEar, whitePaint)
    drawLine(canvas, nose, rightEyeInner, whitePaint)
    drawLine(canvas, rightEyeInner, rightEye, whitePaint)
    drawLine(canvas, rightEye, rightEyeOuter, whitePaint)
    drawLine(canvas, rightEyeOuter, rightEar, whitePaint)
    drawLine(canvas, leftMouth, rightMouth, whitePaint)

    drawLine(canvas, leftShoulder, rightShoulder, whitePaint)
    drawLine(canvas, leftHip, rightHip, whitePaint)

    // Левая часть тела
    drawLine(canvas, leftShoulder, leftElbow, leftPaint)
    drawLine(canvas, leftElbow, leftWrist, leftPaint)
    drawLine(canvas, leftShoulder, leftHip, leftPaint)
    drawLine(canvas, leftHip, leftKnee, leftPaint)
    drawLine(canvas, leftKnee, leftAnkle, leftPaint)
    drawLine(canvas, leftWrist, leftThumb, leftPaint)
    drawLine(canvas, leftWrist, leftPinky, leftPaint)
    drawLine(canvas, leftWrist, leftIndex, leftPaint)
    drawLine(canvas, leftIndex, leftPinky, leftPaint)
    drawLine(canvas, leftAnkle, leftHeel, leftPaint)
    drawLine(canvas, leftHeel, leftFootIndex, leftPaint)

    // Правая часть тела
    drawLine(canvas, rightShoulder, rightElbow, rightPaint)
    drawLine(canvas, rightElbow, rightWrist, rightPaint)
    drawLine(canvas, rightShoulder, rightHip, rightPaint)
    drawLine(canvas, rightHip, rightKnee, rightPaint)
    drawLine(canvas, rightKnee, rightAnkle, rightPaint)
    drawLine(canvas, rightWrist, rightThumb, rightPaint)
    drawLine(canvas, rightWrist, rightPinky, rightPaint)
    drawLine(canvas, rightWrist, rightIndex, rightPaint)
    drawLine(canvas, rightIndex, rightPinky, rightPaint)
    drawLine(canvas, rightAnkle, rightHeel, rightPaint)
    drawLine(canvas, rightHeel, rightFootIndex, rightPaint)

    // Строим inFrameLikelihood для всех точек
    if (showInFrameLikelihood) {
      for (landmark in landmarks) {
        canvas.drawText(
          String.format(Locale.US, "%.2f", landmark.inFrameLikelihood),
          translateX(landmark.position.x),
          translateY(landmark.position.y),
          whitePaint
        )
      }
    }
  }
// Рисует точку на холсте на основе координат указанной точки позы с заданным стилем рисования.

  internal fun drawPoint(canvas: Canvas, landmark: PoseLandmark, paint: Paint) {
    val point = landmark.position3D
    maybeUpdatePaintColor(paint, canvas, point.z)
    canvas.drawCircle(translateX(point.x), translateY(point.y), DOT_RADIUS, paint)
  }
//функция предназначена для рисования линии между двумя заданными точками позы на заданном холсте с использованием заданного стиля рисования.
  internal fun drawLine(
    canvas: Canvas,
    startLandmark: PoseLandmark?,
    endLandmark: PoseLandmark?,
    paint: Paint
  ) {
    val start = startLandmark!!.position3D
      val end = endLandmark!!.position3D

    // Получает среднее значение z для текущей линии тела
    val avgZInImagePixel = (start.z + end.z) / 2
    maybeUpdatePaintColor(paint, canvas, avgZInImagePixel)

    canvas.drawLine(
        translateX(start.x),
        translateY(start.y),
        translateX(end.x),
        translateY(end.y),
        paint
      )
  }
  // Функция для обновления цвета краски на основе значения z
  internal fun maybeUpdatePaintColor(
    paint: Paint,
    canvas: Canvas,
    zInImagePixel: Float
  ) {
    if (!visualizeZ) {
      return
    }

    // Если visualizeZ равен true, то в зависимости от значения z происходит настройка окраски в разные цвета.
    // Получает диапазон значений z.
    val zLowerBoundInScreenPixel: Float
    val zUpperBoundInScreenPixel: Float

    if (rescaleZForVisualization) {
      zLowerBoundInScreenPixel = min(-0.001f, scale(zMin))
      zUpperBoundInScreenPixel = max(0.001f, scale(zMax))
    } else {
      // По умолчанию предполагается, что диапазон значений z в пикселях экрана равен [-canvasWidth, canvasWidth].
      val defaultRangeFactor = 1f
      zLowerBoundInScreenPixel = -defaultRangeFactor * canvas.width
      zUpperBoundInScreenPixel = defaultRangeFactor * canvas.width
    }

    val zInScreenPixel = scale(zInImagePixel)

    if (zInScreenPixel < 0) {
      // Устанавливает краску для рисования линии тела красным цветом, если она находится перед началом оси z.
      // Сопоставляет значения в пределах [zLowerBoundInScreenPixel, 0) с [255, 0) и использует их для управления
      // цвет. Чем больше значение, тем более красным оно будет.
      var v = (zInScreenPixel / zLowerBoundInScreenPixel * 255).toInt()
      v = Ints.constrainToRange(v, 0, 255)
      paint.setARGB(255, 255, 255 - v, 255 - v)
    } else {
      // Настраивает краску, чтобы нарисовать линию тела синим цветом, если она находится за осью z.
      // Сопоставляет значения в пределах [0, zUpperBoundInScreenPixel] с [0, 255] и использует их для управления
      // цвет. Чем больше значение, тем больше синего будет.
      var v = (zInScreenPixel / zUpperBoundInScreenPixel * 255).toInt()
      v = Ints.constrainToRange(v, 0, 255)
      paint.setARGB(255, 255 - v, 255 - v, 255)
    }
  }

  companion object {
    // Константы для рисования и визуализации
    private val DOT_RADIUS = 8.0f
    private val IN_FRAME_LIKELIHOOD_TEXT_SIZE = 30.0f
    private val STROKE_WIDTH = 10.0f
    private val POSE_CLASSIFICATION_TEXT_SIZE = 60.0f
  }
}
