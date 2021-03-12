package com.htf.diva.utils.content

import com.htf.diva.models.WeekDays


class DummyContent {
    companion object {

        fun getWeekDays():ArrayList<WeekDays>{

            val arrWeekDays=ArrayList<WeekDays>()
            var weekDays=WeekDays()
            weekDays.weekDay="Monday"
            weekDays.type=1
            arrWeekDays.add(weekDays)

            weekDays= WeekDays()
            weekDays.weekDay="Tuesday"
            weekDays.type=2
            arrWeekDays.add(weekDays)


            weekDays= WeekDays()
            weekDays.weekDay="Wednesday"
            weekDays.type=3
            arrWeekDays.add(weekDays)

            weekDays= WeekDays()
            weekDays.weekDay="Thursday"
            weekDays.type=4
            arrWeekDays.add(weekDays)

            weekDays= WeekDays()
            weekDays.weekDay="Friday"
            weekDays.type=5
            arrWeekDays.add(weekDays)

            weekDays= WeekDays()
            weekDays.weekDay="Saturday"
            weekDays.type=6
            arrWeekDays.add(weekDays)

            weekDays= WeekDays()
            weekDays.weekDay="Sunday"
            weekDays.type=7
            arrWeekDays.add(weekDays)


            return arrWeekDays

        }

    }
}