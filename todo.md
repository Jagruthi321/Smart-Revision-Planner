

#  Smart Adaptive Revision Planner — `todo.md`

## Development Strategy

Each chunk follows:

1. Read `spec.md`
2. Implement scoped feature
3. Validate behavior
4. Move to next chunk

---

#  Phase 1: Project Setup & Foundation

##  Chunk 1: Project Initialization

* Create Android project (Kotlin)
* Setup package structure:

  * `ui/`
  * `domain/`
  * `data/`
* Add dependencies:

  * Room
  * Retrofit (for APIs)
  * Coroutines / Flow
* Setup MVI base classes:

  * Intent
  * State
  * Reducer

 Output:

* Clean base architecture ready

---

##  Chunk 2: Local JSON Setup

* Create static JSON:

  * Subjects
  * Chapters
  * Importance
* Place in `assets/`
* Build JSON parser

Output:

* App can load syllabus locally

---

##  Chunk 3: Room Database Setup

* Create `ChapterEntity`
* Create DAO:

  * Insert
  * Update
  * Fetch
* Setup Room DB

 Output:

* Local persistence working

---

#  Phase 2: Core Planning Engine

##  Chunk 4: Plan Input UI (Setup Flow)

* Screen 1: Exam date input
* Screen 2: Subject selection
* Screen 3: Chapter selection
* Screen 4: Difficulty assignment

 Output:

* User can input planning data

---

##  Chunk 5: Planner Engine (Core Logic)

* Implement:

  * Total days calculation
  * Difficulty weights
  * Workload per day
* Distribute chapters across days

 Output:

* Day-wise schedule generated

---

##  Chunk 6: Save Generated Plan

* Store schedule in Room
* Map chapters → assignedDate

 Output:

* Plan persists across sessions

---

#  Phase 3: Dashboard & Tracking

## Chunk 7: Plan Dashboard UI

* Show:

  * Today’s tasks
  * Progress bar
  * Upcoming schedule
* Add:

  * Complete button
  * Skip button

Output:

* Main screen functional

---

## Chunk 8: Progress Tracking Logic

* Track:

  * Completed chapters
  * Daily progress %
  * Overall progress %
* Update state on user actions

 Output:

* Real-time progress updates

---

##  Chunk 9: Chapter Completion Flow

* On complete:

  * Update status
  * Store timestamp
  * Remove from today list
* Persist changes

 Output:

* Completion flow working

---

# Phase 4: Adaptive Intelligence

##  Chunk 10: Missed Task Detection

* Detect:

  * Tasks not completed for a day
* Trigger rescheduling

 Output:

* System detects missed work

---

##  Chunk 11: Rescheduler Engine

* Redistribute missed chapters:

  * Even distribution
  * Apply constraints:

    * +25% OR +1 difficulty unit
* Update future schedule

 Output:

* Adaptive scheduling working

---

##  Chunk 12: Overload Handling

* Detect:

  * Workload > available days
* Implement:

  * Priority scoring
  * Optional chapter list

Output:

* System handles impossible plans

---

#  Phase 5: Learning Support

##  Chunk 13: Wikipedia Integration

* Fetch summaries using topicKeyword
* Show in Chapter Detail screen

 Output:

* Concept lookup working

---

##  Chunk 14: Quotes Integration

* Fetch motivational quotes
* Display on dashboard

 Output:

* Motivation feature added

---

##  Chunk 15: API Failure Handling

* Add:

  * Fallback content
  * Retry mechanism
* Cache responses

 Output:

* Graceful degradation implemented

---

#  Phase 6: UX Polish

##  Chunk 16: Dashboard Improvements

* Add:

  * Alerts (missed tasks)
  * Better UI hierarchy
* Improve usability

 Output:

* Clean, intuitive UI

---

##  Chunk 17: Chapter Detail Screen

* Show:

  * Chapter info
  * Difficulty
  * Summary
* Add notes support

 Output:

* Detailed learning view

---

##  Chunk 18: Completion Summary Screen

* Show:

  * Total completed
  * Consistency score

Output:

* End-state experience ready

---

#  Phase 7: Optimization & Stability

##  Chunk 19: Performance Optimization

* Optimize:

  * DB queries
  * State updates
* Ensure smooth performance on low-end devices

Output:

* App runs efficiently

---

##  Chunk 20: Edge Case Handling

* Handle:

  * Empty plan
  * All tasks skipped
  * No internet
* Improve stability

 Output:

* Robust app behavior

---

#  Phase 8: Testing & Finalization

##  Chunk 21: Unit Testing

* Test:

  * Planner engine
  * Rescheduler logic
  * Priority algorithm

Output:

* Core logic verified

---

##  Chunk 22: Manual Testing

* Test flows:

  * Plan creation
  * Daily usage
  * Missed tasks
  * Completion

 Output:

* End-to-end validation

---

##  Chunk 23: Final Cleanup

* Refactor code
* Improve naming
* Add comments

 Output:

* Production-quality codebase

---

#  Development Loop (How You Use This)

For each chunk:

1. Open `todo.md`
2. Pick current chunk
3. Tell your AI/dev tool:

   > “Implement Chunk X based on spec.md and rules”
4. Review output
5. Test locally
6. Move forward

---

#  Final Outcome

By completing all chunks, you will have:

* Fully functional Android app
* MVI-based architecture
* Adaptive planning engine
* Offline-first reliability
* Strong portfolio project

