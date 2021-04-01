import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ComsetDetailComponent } from './comset-detail.component';

describe('Component Tests', () => {
  describe('Comset Management Detail Component', () => {
    let comp: ComsetDetailComponent;
    let fixture: ComponentFixture<ComsetDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ComsetDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ comset: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ComsetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComsetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load comset on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comset).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
