import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DXsDetailComponent } from './d-xs-detail.component';

describe('Component Tests', () => {
  describe('DXs Management Detail Component', () => {
    let comp: DXsDetailComponent;
    let fixture: ComponentFixture<DXsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DXsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dXs: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DXsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DXsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dXs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dXs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
