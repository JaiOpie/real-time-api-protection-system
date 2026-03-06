<template>
  <div>
    <canvas ref="chart"></canvas>
  </div>
</template>

<script>
import { Chart } from "chart.js/auto"

export default {

  props: ["requests"],

  data() {
    return {
      chart: null
    }
  },

  mounted() {
    this.renderChart()
  },

  watch: {
    requests() {
      this.updateChart()
    }
  },

  methods: {

    groupRequests() {

      const counts = {}

      this.requests.forEach(r => {

        const time = new Date(r.timestamp)
          .toLocaleTimeString()

        counts[time] = (counts[time] || 0) + 1
      })

      return counts
    },

    renderChart() {

      const ctx = this.$refs.chart

      const grouped = this.groupRequests()

      this.chart = new Chart(ctx, {

        type: "line",

        data: {
          labels: Object.keys(grouped),

          datasets: [
            {
              label: "Requests",
              data: Object.values(grouped),
              borderColor: "blue",
              tension: 0.3
            }
          ]
        }

      })
    },

    updateChart() {

      if (!this.chart) return

      const grouped = this.groupRequests()

      this.chart.data.labels = Object.keys(grouped)
      this.chart.data.datasets[0].data = Object.values(grouped)

      this.chart.update()
    }

  }

}
</script>