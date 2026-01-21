package m68k.common;

public final class DataSize implements Comparable<DataSize> {
    private static final int KB = 1024;
    private static final int MB = KB * 1024;

    private final int bytes;

    private DataSize(int bytes) {
        if (bytes < 0) throw new IllegalArgumentException("bytes must be >= 0");
        this.bytes = bytes;
    }

    public static DataSize ofBytes(int bytes) {
        return new DataSize(bytes);
    }

    public static DataSize ofKilobytes(int kb) {
        return new DataSize(Math.multiplyExact(kb, KB));
    }

    public static DataSize ofMegabytes(int mb) {
        return new DataSize(Math.multiplyExact(mb, MB));
    }

    public int toBytes() {
        return bytes;
    }

    public int toKilobytes() {
        return bytes / KB;
    }

    public int toMegabytes() {
        return bytes / MB;
    }

    public DataSize plus(DataSize other) {
        return new DataSize(Math.addExact(this.bytes, other.bytes));
    }

    public DataSize minus(DataSize other) {
        int result = this.bytes - other.bytes;
        if (result < 0) throw new IllegalArgumentException("result would be negative");
        return new DataSize(result);
    }

    @Override
    public int compareTo(DataSize o) {
        return Integer.compare(this.bytes, o.bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSize)) return false;
        return bytes == ((DataSize) o).bytes;
    }

    @Override
    public int hashCode() {
        return bytes;
    }

    @Override
    public String toString() {
        if (bytes % MB == 0) return (bytes / MB) + "MB";
        if (bytes % KB == 0) return (bytes / KB) + "KB";
        return bytes + "B";
    }
}
