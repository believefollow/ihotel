import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClassBakDetailComponent } from './class-bak-detail.component';

describe('Component Tests', () => {
  describe('ClassBak Management Detail Component', () => {
    let comp: ClassBakDetailComponent;
    let fixture: ComponentFixture<ClassBakDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ClassBakDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ classBak: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ClassBakDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassBakDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classBak on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classBak).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
