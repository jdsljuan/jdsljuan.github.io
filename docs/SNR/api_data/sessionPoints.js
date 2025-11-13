const TOTAL_POINTS_MANAGER = {
    // 🔑 The key under which the data will be stored in Local Storage
    STORAGE_KEY: 'TotalSessionPoints',

    /**
     * Sets or updates the total session points.
     * @param {number} points - The new total points to set.
     */
    setPoints(points) {
        if (typeof points !== 'number' || points < 0) {
            console.error("Invalid points value. Must be a non-negative number.");
            return;
        }
        // Local Storage only stores strings, so we convert the number.
        localStorage.setItem(this.STORAGE_KEY, points.toString());
        console.log(`✅ TotalSessionPoints set to: ${points}`);
    },

    /**
     * Retrieves the current total session points.
     * @returns {number} The current total points, or 0 if not found.
     */
    getPoints() {
        const storedValue = localStorage.getItem(this.STORAGE_KEY);

        if (storedValue === null) {
            // Key not found, return 0 as a default/initial value
            return 0;
        }

        // Local Storage returns a string, so we parse it to an integer.
        const points = parseInt(storedValue, 10);

        // Basic validation for stored data integrity
        if (isNaN(points)) {
             console.error("Stored value is corrupted or not a number. Returning 0.");
             // Optionally, you could also call this.deletePoints() here to clean up the bad data.
             return 0;
        }

        return points;
    },

    /**
     * Deletes the TotalSessionPoints entry from Local Storage.
     */
    deletePoints() {
        localStorage.removeItem(this.STORAGE_KEY);
        console.log(`🗑️ TotalSessionPoints deleted from Local Storage.`);
    },

    /**
     * Adds a specified amount of points to the current total.
     * Creates the entry if it doesn't exist.
     * @param {number} amount - The number of points to add.
     */
    addPoints(amount) {
        if (typeof amount !== 'number' || amount == 0) {
            console.error("Invalid amount. Must be a positive number to add.");
            return;
        }

        const currentPoints = this.getPoints(); // Retrieves current value (or 0)
        const newTotal = currentPoints + amount;

        // Uses the existing setPoints method to save the new total
        this.setPoints(newTotal);
        console.log(`Added ${amount} points. New Total: ${newTotal}`);

        return newTotal;
    }
};