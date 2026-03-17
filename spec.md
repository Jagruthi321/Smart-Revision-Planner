
# Smart Adaptive Revision Planner — `spec.md`

## 1. Overview

The Smart Adaptive Revision Planner is an Android-native, offline-first application designed to help Class 11 and 12 students preparing for competitive exams (JEE, NEET, Intermediate) efficiently plan, track, and adapt their revision schedules.

The system dynamically adjusts study plans based on user progress, missed tasks, and difficulty levels, while providing lightweight learning support via external APIs.

---

## 2. Goals

* Enable structured day-wise revision planning
* Adapt schedules dynamically based on user behavior
* Provide clear progress tracking
* Support quick concept lookup during revision
* Maintain reliability with offline-first architecture
* Demonstrate strong engineering practices (MVI, modular design)

---

## 3. Non-Goals

* No user authentication
* No backend/server-side logic
* No real-time sync across devices
* No heavy analytics or ML personalization

---

## 4. Target Users

**Primary Users:**

* Class 11 & 12 students
* Preparing for competitive exams (JEE, NEET, Intermediate)

**User Traits:**

* Large syllabus
* Limited time
* Irregular study patterns
* Need structured but flexible planning

---

## 5. Core Features

### 5.1 Plan Generation

#### Inputs

* Exam date (mandatory)
* Subjects (mandatory)
* Chapters (mandatory, from local JSON)
* Difficulty per chapter (mandatory: Easy / Medium / Hard)
* Study hours per day (optional)

#### Logic

* Calculate total available days
* Assign difficulty weights:

  * Easy = 1
  * Medium = 2
  * Hard = 3
* Compute:

  ```
  workload_per_day = total_weight / total_days
  ```
* Distribute chapters:

  * Balanced subject mix
  * Daily workload ≈ workload_per_day
* Reserve last few days for revision

---

### 5.2 Adaptive Rescheduling

#### Trigger

* Missed or skipped tasks

#### Behavior

* Even redistribution across remaining days
* Apply cap:

  * Max increase = +25% OR +1 difficulty unit (whichever is smaller)

#### System Feedback

* Display alert:

  * “Missed tasks have been rescheduled”

---

### 5.3 Difficulty-Based Planning

* Hard chapters receive higher allocation
* Medium receive moderate allocation
* Easy receive minimal allocation

---

### 5.4 Progress Tracking

Tracks:

* Chapter status (Pending / Completed / Skipped)
* Daily completion rate
* Overall progress (%)
* Remaining workload

---

### 5.5 Learning Support

* Fetch summaries via Wikipedia API
* Display motivational quotes via ZenQuotes API

---

### 5.6 Offline-First Design

* Static JSON for:

  * Subjects
  * Chapters
  * Importance
* Core functionality works without internet

---

## 6. User Flow

### 6.1 App Launch

* Home screen
* Motivational quote
* CTA: “Create Study Plan”

---

### 6.2 Plan Setup

1. Enter exam date (+ optional study hours)
2. Select subjects
3. Select chapters (from local JSON)
4. Assign difficulty
5. Generate plan

---

### 6.3 Plan Dashboard

#### Priority Order

1. Today’s Tasks
2. Progress Overview
3. Missed/Rescheduled Alerts
4. Upcoming Schedule (next 2–3 days)
5. Motivational Quote
6. Quick Actions

---

### 6.4 Chapter Interaction

#### On Complete

* Update chapter status
* Store completion timestamp
* Remove from current day list
* Recalculate:

  * Daily progress
  * Overall progress
* Persist in local database
* Perform light schedule recalibration
* Provide visual feedback

---

### 6.5 Daily Usage Loop

* Open app
* View today’s tasks
* Mark progress
* System adapts schedule

---

### 6.6 Completion State

Trigger:

* All chapters completed OR exam date reached

Display:

* Summary screen:

  * Total completed
  * Consistency score

---

## 7. Technical Architecture

### 7.1 Architecture Pattern

* MVI (Model-View-Intent)

---

### 7.2 Layers

#### UI Layer

* State-driven UI (Compose/XML)
* Renders State

#### Domain Layer

* Planner Engine
* Rescheduler Engine
* Priority Engine

#### Data Layer

* Room Database
* Local JSON parser
* API handlers

---

### 7.3 Design Principles

* SOLID principles
* Modular components
* Clear separation of concerns

---

## 8. Data Model

### Chapter Entity

#### Basic Info

* id: String
* name: String
* subject: String

#### Planning

* difficulty: Enum (Easy / Medium / Hard)
* difficultyWeight: Int (1 / 2 / 3)
* estimatedHours: Float

#### Scheduling

* assignedDate: Date
* isScheduled: Boolean

#### Progress

* status: Enum (Pending / Completed / Skipped)
* completionTimestamp: Long

#### Learning

* topicKeyword: String
* notes: String?

#### Metadata

* priority: Enum (High / Medium / Low)
* revisionCount: Int

---

## 9. Priority Algorithm

### Formula

```
priorityScore = (difficultyWeight * 0.4)
              + (importanceWeight * 0.4)
              + (revisionNeed * 0.2)
```

### Logic

* Sort chapters by priorityScore (descending)
* Select chapters until capacity fits
* Remaining → optional list

---

## 10. Edge Cases

### 10.1 Overloaded Plan

If workload > available capacity:

* Show warning:

  * “Your plan is overloaded”

#### Options:

* Increase effort
* Reduce scope
* Continue with intensive schedule

---

### 10.2 API Failure

* Use cached or default content
* Do not block core functionality
* Allow retry

---

### 10.3 Missed Days

* Controlled redistribution
* Prevent sudden workload spikes

---

## 11. Storage Strategy

### Room Database

Stores:

* Chapters
* Schedule
* Progress

### Static JSON

Stores:

* Subjects
* Chapters metadata
* Importance levels

---

## 12. External APIs

* Wikipedia API → concept summaries
* ZenQuotes API → motivational quotes

#### Behavior

* Non-critical
* Graceful fallback

---

## 13. Constraints

* No login
* No backend
* Offline-first architecture
* Minimal permissions
* Optimized for low-end devices
* Clean and simple UI
* Lightweight API usage

---

## 14. Risks & Limitations

* Wikipedia content inconsistency
* Static difficulty assumptions
* No cross-device sync
* Limited personalization
* Static syllabus constraints

---

## 15. Summary

The system is a:

* Smart adaptive planner
* Offline-first Android application
* State-driven (MVI) architecture
* Reliable and lightweight solution

It demonstrates:

* Product thinking
* System design
* Real-world engineering trade-offs
