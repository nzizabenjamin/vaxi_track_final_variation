package com.finalprojectgroupae.immunization.data;

import com.finalprojectgroupae.immunization.R;
import com.finalprojectgroupae.immunization.data.model.Appointment;
import com.finalprojectgroupae.immunization.data.model.Child;
import com.finalprojectgroupae.immunization.data.model.DashboardStat;
import com.finalprojectgroupae.immunization.data.model.Reminder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class DemoDataProvider {

    private DemoDataProvider() {
    }

    public static List<Child> getPriorityChildren() {
        return new ArrayList<>(Arrays.asList(
                new Child(
                        "26082",
                        "Ruth Gwiza",
                        "Self-tracked member",
                        "Gitega Center",
                        "HPV Booster",
                        "Due 21 Jul",
                        "Due soon",
                        26,
                        R.color.colorAccent
                ),
                new Child(
                        "26240",
                        "Nziza Benjamin",
                        "Community liaison",
                        "Kigali North",
                        "MR-1",
                        "Overdue since 10 Jul",
                        "Overdue 5 days",
                        30,
                        R.color.colorWarning
                ),
                new Child(
                        "24881",
                        "Cyubahiro Eddy Prince",
                        "Cyubahiro Household",
                        "Nyamirambo",
                        "Penta-3",
                        "Due 24 Jul",
                        "Due soon",
                        18,
                        R.color.colorPrimary
                ),
                new Child(
                        "25583",
                        "Baraka Johnson Bright",
                        "Bright Care Team",
                        "Rubavu Coast",
                        "PCV Booster",
                        "Completed 12 Jul",
                        "Completed",
                        28,
                        R.color.colorSuccess
                ),
                new Child(
                        "25713",
                        "Tonkuba Decon",
                        "Village mentor",
                        "Kayonza Ridge",
                        "MR-2",
                        "Due 05 Aug",
                        "Scheduled",
                        34,
                        R.color.colorSecondary
                ),
                new Child(
                        "26078",
                        "Manira Theoneste",
                        "Mother: Emerithe",
                        "Musanze South",
                        "OPV Booster",
                        "Overdue since 03 Jul",
                        "Overdue 7 days",
                        22,
                        R.color.colorWarning
                ),
                new Child(
                        "25231",
                        "Kidda Vinah",
                        "Caretaker: Salima",
                        "Kirehe Plains",
                        "MR Catch-up",
                        "Due 19 Jul",
                        "Due soon",
                        20,
                        R.color.colorAccent
                ),
                new Child(
                        "26506",
                        "Munezero Eugene",
                        "Eugene Household",
                        "Gasabo Heights",
                        "Typhoid Booster",
                        "Scheduled 01 Aug",
                        "Scheduled",
                        32,
                        R.color.colorSecondary
                ),
                new Child(
                        "26084",
                        "Serge Dukuziyaremye",
                        "Guardian: Mathilde",
                        "Huye Hub",
                        "Yellow Fever",
                        "Completed 02 Jul",
                        "Completed",
                        36,
                        R.color.colorSuccess
                ),
                new Child(
                        "25948",
                        "Ineza 74",
                        "Self-tracked member",
                        "Muhanga Crossing",
                        "MR-1",
                        "Overdue since 08 Jul",
                        "Overdue 9 days",
                        27,
                        R.color.colorError
                )
        ));
    }

    public static Child getFeaturedChild() {
        List<Child> children = getPriorityChildren();
        return children.isEmpty() ? null : children.get(0);
    }

    public static List<Appointment> getUpcomingAppointments() {
        return Arrays.asList(
                new Appointment(
                        "Village 12 Outreach",
                        "Kisiwa Community Hall",
                        "Mon • 17 Jun",
                        "09:00 AM",
                        "Outreach team",
                        R.color.colorPrimary
                ),
                new Appointment(
                        "Clinic follow-up",
                        "Makutano Health Centre",
                        "Tue • 18 Jun",
                        "11:30 AM",
                        "Facility visit",
                        R.color.colorSecondary
                ),
                new Appointment(
                        "Mobile cold-chain drop",
                        "Baricho Market",
                        "Thu • 20 Jun",
                        "08:00 AM",
                        "Logistics",
                        R.color.colorAccent
                )
        );
    }

    public static List<Reminder> getReminderQueue() {
        return Arrays.asList(
                new Reminder(
                        "SMS",
                        "Grace Gathoni (+2547 11 222 333)",
                        "Amina is overdue for Penta-3. Outreach visit on Mon 17 Jun.",
                        "Scheduled via FHIR Communication",
                        R.color.colorPrimary
                ),
                new Reminder(
                        "Push",
                        "Makutano Facility",
                        "Confirm session readiness for 20 Jun clinic day.",
                        "Acknowledged",
                        R.color.colorSecondary
                ),
                new Reminder(
                        "WhatsApp",
                        "Village CHW Team",
                        "3 defaulters require home visit before Friday.",
                        "Pending send",
                        R.color.colorAccent
                )
        );
    }

    public static List<DashboardStat> getDashboardStats() {
        return Arrays.asList(
                new DashboardStat("Due today", "12", "4 infants • 8 toddlers", R.color.colorPrimary),
                new DashboardStat("Overdue", "4", "Escalated to CHW", R.color.colorWarning),
                new DashboardStat("Completed", "38", "Last 7 days", R.color.colorSuccess),
                new DashboardStat("Outreach sessions", "6", "Next 14 days", R.color.colorSecondary)
        );
    }

    public static List<String> getChildTimeline() {
        return Arrays.asList(
                "BCG • Completed at birth clinic",
                "Penta-1 • Completed 14 Feb",
                "Penta-2 • Completed 14 Apr",
                "Penta-3 • Due 18 Jun",
                "MR-1 • Scheduled 20 Jun"
        );
    }

    public static List<String> getCarePlanNotes() {
        return Collections.singletonList("Catch-up plan approved by FHIR PlanDefinition v2.1");
    }
}

