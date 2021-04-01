export interface IBookYst {
  id?: number;
  roomcode?: string | null;
  roomname?: string;
  roomnum?: string | null;
  roomseparatenum?: string | null;
  bedids?: string | null;
  bedsimpledesc?: string | null;
  bednum?: string | null;
  roomsize?: string | null;
  roomfloor?: string | null;
  netservice?: string | null;
  nettype?: string | null;
  iswindow?: string | null;
  remark?: string | null;
  sortid?: string | null;
  roomstate?: string | null;
  source?: string | null;
  roomamenities?: string | null;
  maxguestnums?: string | null;
  roomdistribution?: string | null;
  conditionbeforedays?: string | null;
  conditionleastdays?: string | null;
  conditionleastroomnum?: string | null;
  paymentype?: string | null;
  rateplandesc?: string | null;
  rateplanname?: string | null;
  rateplanstate?: string | null;
  addvaluebednum?: string | null;
  addvaluebedprice?: string | null;
  addvaluebreakfastnum?: string | null;
  addvaluebreakfastprice?: string | null;
  baseprice?: string | null;
  saleprice?: string | null;
  marketprice?: string | null;
  hotelproductservice?: string | null;
  hotelproductservicedesc?: string | null;
  hotelproductid?: string | null;
  roomid?: string | null;
  hotelid?: string | null;
}

export class BookYst implements IBookYst {
  constructor(
    public id?: number,
    public roomcode?: string | null,
    public roomname?: string,
    public roomnum?: string | null,
    public roomseparatenum?: string | null,
    public bedids?: string | null,
    public bedsimpledesc?: string | null,
    public bednum?: string | null,
    public roomsize?: string | null,
    public roomfloor?: string | null,
    public netservice?: string | null,
    public nettype?: string | null,
    public iswindow?: string | null,
    public remark?: string | null,
    public sortid?: string | null,
    public roomstate?: string | null,
    public source?: string | null,
    public roomamenities?: string | null,
    public maxguestnums?: string | null,
    public roomdistribution?: string | null,
    public conditionbeforedays?: string | null,
    public conditionleastdays?: string | null,
    public conditionleastroomnum?: string | null,
    public paymentype?: string | null,
    public rateplandesc?: string | null,
    public rateplanname?: string | null,
    public rateplanstate?: string | null,
    public addvaluebednum?: string | null,
    public addvaluebedprice?: string | null,
    public addvaluebreakfastnum?: string | null,
    public addvaluebreakfastprice?: string | null,
    public baseprice?: string | null,
    public saleprice?: string | null,
    public marketprice?: string | null,
    public hotelproductservice?: string | null,
    public hotelproductservicedesc?: string | null,
    public hotelproductid?: string | null,
    public roomid?: string | null,
    public hotelid?: string | null
  ) {}
}

export function getBookYstIdentifier(bookYst: IBookYst): number | undefined {
  return bookYst.id;
}
