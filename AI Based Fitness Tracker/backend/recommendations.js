const baseAdvice = {
  RUNNING: ['Keep an easy conversational pace for recovery runs.', 'Include a short dynamic warm-up before running.'],
  WALKING: ['Increase distance gradually and keep a comfortable pace.', 'Use supportive footwear and hydrate on longer walks.'],
  CYCLING: ['Maintain a relaxed grip and a steady cadence.', 'Check bike fit before increasing duration.'],
  SWIMMING: ['Prioritize smooth breathing and controlled technique.', 'Warm up your shoulders before faster intervals.'],
  WEIGHT_TRAINING: ['Focus on controlled form before adding load.', 'Leave time for recovery between sessions targeting the same muscle group.'],
  YOGA: ['Move within a pain-free range of motion.', 'Use slow breathing to support each position.'],
  HIIT: ['Keep high-intensity intervals brief and recover fully.', 'Stop if you feel chest pain, dizziness, or unusual shortness of breath.'],
  CARDIO: ['Build intensity progressively rather than all at once.', 'Cool down for a few minutes after exercise.'],
  STRETCHING: ['Avoid bouncing and hold stretches gently.', 'Stretch after warming up, not from cold.'],
  OTHER: ['Progress gradually and listen to your body.', 'Stay hydrated and recover between demanding sessions.']
};

export async function createRecommendation(activity) {
  const intensity = activity.duration >= 60 || activity.caloriesBurned >= 600 ? 'demanding' : activity.duration >= 30 ? 'moderate' : 'light';
  const suggestions = baseAdvice[activity.type] || baseAdvice.OTHER;
  return {
    activityId: activity.id,
    userId: activity.userId,
    type: activity.type,
    summary: `This was a ${intensity} ${activity.type.toLowerCase().replace('_', ' ')} session lasting ${activity.duration} minutes.`,
    improvements: [activity.duration < 20 ? 'Build duration gradually in 5-minute increments.' : 'Keep a consistent weekly routine and schedule recovery.'],
    suggestions,
    safety: ['Warm up before vigorous exercise.', 'Stay hydrated.', 'Stop and seek medical advice for unusual pain or symptoms.'],
    source: 'built-in-coach',
    createdAt: new Date().toISOString()
  };
}

