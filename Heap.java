import java.util.ArrayList;
import java.util.List;

public class Heap<T extends HeapItem<T>>{

    public List<T> items;
    private int Count;

    public int size() { return Count; }

    public Heap(int maxHeapSize) {
        items = new ArrayList<>(maxHeapSize);
        Count = 0;
    }

    public void Add(T item) {
        item.HeapIndex = Count;
        items.set(Count, item);
        SortUp(item);
        Count++;
    }

    public T RemoveFirst() {
        T firstItem = items.get(0);
        Count--;
        items.set(0, items.get(Count));
        items.get(0).HeapIndex = 0;
        SortDown(items.get(0));
        return firstItem;
    }

    public void UpdateItem(T item) {
        SortUp(item);
        SortDown(item);
    }

    public boolean Contains(T item) {
        return item.equals(items.get(item.HeapIndex));
    }

    void SortDown(T item) {
        while (true) {
            int childIndexLeft = item.HeapIndex * 2 + 1;
            int childIndexRight = item.HeapIndex * 2 + 2;
            int swapIndex = 0;

            if (childIndexLeft < Count) {
                swapIndex = childIndexLeft;

                if (childIndexRight < Count)
                    if (items.get(childIndexLeft).compareTo(items.get(childIndexRight)) < 0)
                        swapIndex = childIndexRight;

                if (item.compareTo(items.get(swapIndex)) < 0)
                    Swap(item, items.get(swapIndex));
                else
                    return;
            }
            else
                return;
        }
    }

    void SortUp(T item) {
        int parentIndex = (item.HeapIndex - 1) / 2;

        while (true) {
            T parentItem = items.get(parentIndex);
            if (item.compareTo(parentItem) > 0)
                Swap(item, parentItem);
            else
                break;

            parentIndex = (item.HeapIndex - 1) / 2;
        }
    }

    void Swap(T itemA, T itemB) {
        items.set(itemA.HeapIndex, itemB);
        items.set(itemB.HeapIndex, itemA);
        int itemAIndex = itemA.HeapIndex;
        itemA.HeapIndex = itemB.HeapIndex;
        itemB.HeapIndex = itemAIndex;
    }
}