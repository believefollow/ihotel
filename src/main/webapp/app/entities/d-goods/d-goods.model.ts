export interface IDGoods {
  id?: number;
  typeid?: number;
  goodsname?: string;
  goodsid?: string;
  ggxh?: string | null;
  pysj?: string | null;
  wbsj?: string | null;
  unit?: string | null;
  gcsl?: number | null;
  dcsl?: number | null;
  remark?: string | null;
}

export class DGoods implements IDGoods {
  constructor(
    public id?: number,
    public typeid?: number,
    public goodsname?: string,
    public goodsid?: string,
    public ggxh?: string | null,
    public pysj?: string | null,
    public wbsj?: string | null,
    public unit?: string | null,
    public gcsl?: number | null,
    public dcsl?: number | null,
    public remark?: string | null
  ) {}
}

export function getDGoodsIdentifier(dGoods: IDGoods): number | undefined {
  return dGoods.id;
}
