# TODO - Inventory module fixes

## Step 1: Inventory movement read-only
- [x] Update `InventoryMovementServiceContract` to only expose movement viewing methods (no record methods).
- [x] Implement `InventoryMovementServiceImpl#getMovements(...)` (pagination).

## Step 2: Inventory service contract alignment
- [ ] Expand `InventoryServiceContract` to declare only the methods needed by ProductService and PurchaseService and InventoryController (stock changes + queries).
- [ ] Implement/complete required methods in `InventoryServiceImpl`: 
  - [ ] `reduceStock(Product, Integer)` with reference+remarks reuse (single method with ref/remarks; keep only that overload in usage).
  - [ ] `getInventory(String, Boolean, Pageable)`.
  - [ ] Fix `adjustStock(UUID, StockAdjustmentRequestDto)` to use sign of quantity (+ increase / - decrease).
  - [ ] Wrap audit logs in try/catch in all stock-changing methods.

## Step 3: Auto-create inventory on product creation
- [ ] Inject `InventoryServiceContract` into `ProductServiceImpl`.
- [ ] Call `inventoryService.createInventory(savedProduct)` right after product save.

## Step 4: Purchase receiving stock integration
- [ ] Update `PurchaseServiceImpl.receivePurchase(...)` to remove any duplicate/missing inventory movement recording.
- [ ] Ensure it relies on `inventoryService.receiveStock(...)` to both update stock + save inventory movement.

## Step 5: Build & validate
- [ ] Run `mvn -q -DskipTests package`.
- [ ] Fix compilation errors if any.

