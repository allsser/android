package com.example.camera;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MnistModelConfig {
    public static String MODEL_FILENAME = "mnist_model.tflite";

    public static final int INPUT_IMG_SIZE_WIDTH = 28;
    public static final int INPUT_IMG_SIZE_HEIGHT = 28;
    public static final int FLOAT_TYPE_SIZE = 4;
    public static final int PIXEL_SIZE = 1;
    public static final int MODEL_INPUT_SIZE = FLOAT_TYPE_SIZE * INPUT_IMG_SIZE_WIDTH * INPUT_IMG_SIZE_HEIGHT * PIXEL_SIZE;

    public static final List<String> OUTPUT_LABELS = Collections.unmodifiableList(
            Arrays.asList("영", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구"));

    //Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));

    public static final int MAX_CLASSIFICATION_RESULTS = 3;
    public static final float CLASSIFICATION_THRESHOLD = 0.1f;
}
