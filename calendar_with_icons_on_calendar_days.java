package com.example.wou;

import android.graphics.Color;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.wou.navigation.UpperMenuButtonsInit;

public class CalendarPage extends AppCompatActivity {

    private com.applandeo.materialcalendarview.CalendarView calendarView;
    private Button addButton;
    private List<Workout> workouts = new ArrayList<>();
    private LinearLayout workoutsLinearLayout;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Date currentDate;
    private HashSet<String> trainingDays = new HashSet<>();
    private final Locale locale = Locale.getDefault(); // Error in this line, i used another locale mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        calendarView = findViewById(R.id.workouts_calendar);
        addButton = findViewById(R.id.add_workout_in_calendar_button);
        workoutsLinearLayout = findViewById(R.id.workouts_into_calendar_linear_layout);

        addButton.setOnClickListener(v -> showAddWorkoutDialog());

        // Устанавливаем русский Locale
        Locale ruLocale = new Locale("ru");
        Calendar calendar = Calendar.getInstance(ruLocale);
        calendarView.setOnDayClickListener(this::onDayClick);

        currentDate = calendar.getTime();
        updateWorkoutList();
        updateCalendarView();

    }
  
    // I think its old way to do this
    private void onDayClick(EventDay eventDay) { 
        Calendar clickedDayCalendar = eventDay.getCalendar();
        currentDate = clickedDayCalendar.getTime();
        if(hasWorkoutsForDate(currentDate)){
            showWorkoutInfoDialog(currentDate);
        }
    }

    private void showAddWorkoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_workout_into_calendar, null);
        builder.setView(dialogView);

        EditText workoutTitleEditText = dialogView.findViewById(R.id.workoutTitleEditText);
        com.applandeo.materialcalendarview.CalendarView datePicker = dialogView.findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance(locale);
        calendar.setTime(currentDate);
        datePicker.setDate(currentDate);
        final Date[] selectedDate = {currentDate};
        datePicker.setOnDayClickListener(eventDay -> {
            Calendar clickedDayCalendar = eventDay.getCalendar();
            selectedDate[0] = clickedDayCalendar.getTime();
        });

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String title = workoutTitleEditText.getText().toString();
            if (title.isEmpty()) {
                Toast.makeText(CalendarPage.this, "Введите название тренировки", Toast.LENGTH_SHORT).show();
                return;
            }
            addWorkout(selectedDate[0], title);
            currentDate = selectedDate[0];
            updateWorkoutList();
            updateCalendarView();
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addWorkout(Date date, String title) {
        workouts.add(new Workout(dateFormat.format(date), title));
        trainingDays.add(dateFormat.format(date)); // Here I add a workout, and accordingly its date for installing the icon
        Toast.makeText(this, "Добавлено: " + title + " " + getFormattedTime(date), Toast.LENGTH_SHORT).show();
    }

    private void updateCalendarView() {
        List<CalendarDay> calendarDays = new ArrayList<>();
        for (String dateString : trainingDays) {
            try {
                Date date = dateFormat.parse(dateString);
                Calendar calendar = Calendar.getInstance(locale);
                calendar.setTime(date);
                CalendarDay calendarDay = new CalendarDay(calendar);
                calendarDay.setImageResource(R.drawable.workouts_icon);
                calendarDays.add(calendarDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        calendarView.setCalendarDays(calendarDays);
    }


    // Класс для хранения тренировки
    private static class Workout {
        private String date;
        private String title;

        public Workout(String date, String title) {
            this.date = date;
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public String getTitle() {
            return title;
        }
    }
}
